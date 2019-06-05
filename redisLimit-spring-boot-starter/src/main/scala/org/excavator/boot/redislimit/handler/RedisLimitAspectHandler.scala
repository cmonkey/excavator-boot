package org.excavator.boot.redislimit.handler

import java.lang.reflect.Method
import java.util

import javax.annotation.Resource
import org.apache.commons.lang3.StringUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.{Around, Aspect}
import org.aspectj.lang.reflect.MethodSignature
import org.excavator.boot.redislimit.annotation.RedisLimiter
import org.excavator.boot.redislimit.service.RedisLimitService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.stereotype.Component

@Aspect
@Component
@EnableAspectJAutoProxy(exposeProxy = true)
class RedisLimitAspectHandler {
  val logger = LoggerFactory.getLogger(classOf[RedisLimitAspectHandler])

  @Resource
  val redisLimitService:RedisLimitService = null

  @Resource
  val redisLimitExecute: RedisLimitExecute = null

  @Around(value = "@annotation(redisLimiter)")
  def around(joinPoint: ProceedingJoinPoint, redisLimiter: RedisLimiter) = {

    val startTime = System.currentTimeMillis()

    val signature = joinPoint.getSignature()

    if(!signature.isInstanceOf[MethodSignature]){
      throw new IllegalArgumentException("the annoation @RedisLimiter must used on Method")
    }else{

      val methodName = getMehtod(joinPoint).getName()

      var key = redisLimiter.key()

      if (StringUtils.isBlank(key)) {
        key = getName(key, signature.asInstanceOf[MethodSignature])
      }

      val limit = redisLimiter.limit()
      val expireTime = redisLimiter.expire()

      logger.info(s"redis limiter method = $methodName, key = $key, limit = $limit, expire = $expireTime")

      val keys = util.Collections.singletonList[String](key)

      val result = redisLimitExecute.execute(keys, limit, expireTime)

      logger.info(s"redis limiter time = ${System.currentTimeMillis - startTime}")

      if (0 == result) {
        logger.warn("redis limiter result is zero")
        redisLimitService.limit()
      } else {
        joinPoint.proceed()
      }
    }


  }

  def getMehtod(joinPoint: ProceedingJoinPoint): Method = {
     val signature = joinPoint.getSignature().asInstanceOf[MethodSignature]

     var method = signature.getMethod

     if(method.getDeclaringClass().isInterface()){
       method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), method.getParameterTypes():_*)
     }

     method
  }

  def getName(annoationName: String, signature: MethodSignature) : String = {
    if(annoationName.isEmpty()){
      "%s:%s".format(signature.getDeclaringTypeName(), signature.getMethod().getName())
    }else{
      annoationName
    }
  }
}
