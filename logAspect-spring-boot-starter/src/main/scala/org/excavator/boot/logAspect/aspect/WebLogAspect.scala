package org.excavator.boot.logAspect.aspect

import java.util
import java.util.stream.Collectors

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.{AfterReturning, Aspect, Before, Pointcut}
import org.excavator.boot.common.helper.RemoteIpHelper
import org.excavator.boot.common.utils.{FastJsonUtils, IPThreadLocal}
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.{RequestContextHolder, ServletRequestAttributes}

@Order(-1)
@Aspect
@Component
@EnableAspectJAutoProxy
class WebLogAspect {
  val logger = LoggerFactory.getLogger(classOf[WebLogAspect])

  val startTime = new ThreadLocal[Long]

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  def webLog(): Unit = {}


  @Before("webLog()")
  def doBefore(joinPoint: JoinPoint) = {

    startTime.set(System.nanoTime())

    // 接收到请求，记录请求内容
    val attributes = RequestContextHolder.getRequestAttributes().asInstanceOf[ServletRequestAttributes];
    val  request = attributes.getRequest();

    // 记录下请求内容
    logger.info("URL = {}" ,request.getRequestURL().toString());
    logger.info("HTTP_METHOD = {} " , request.getMethod());
    logger.info("remoteIP = {} " , RemoteIpHelper.getRemoteIpFrom(request));
    logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    val objects = joinPoint.getArgs();

    val  list = util.Arrays.stream(objects).filter(o => {
      if(o.isInstanceOf[HttpServletRequest]){
        false;
      }else {
        !(o.isInstanceOf[HttpServletResponse]);
      }
    }).collect(Collectors.toList());

    logger.info("REQUEST : {}",FastJsonUtils.toJSONString(list));

    IPThreadLocal.setRemoteIp(RemoteIpHelper.getRemoteIpFrom(request));
  }

  @AfterReturning(returning = "ret", pointcut = "webLog()")
  def doAfterReturning(ret: Object) = {
    // 处理完请求，返回内容
      logger.info("RESPONSE : {}", FastJsonUtils.toJSONString(ret));
      logger.info("SPEND TIME : {}", (System.currentTimeMillis() - startTime.get()) / 1000
                                     + "(s)");
      IPThreadLocal.remove();
  }
}
