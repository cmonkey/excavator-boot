package org.excavator.boot.authorization.autoconfigure.mananger.impl.interceptor


import com.alibaba.fastjson.JSON
import javax.annotation.Resource
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.excavator.boot.authorization.annotation.Authorization
import org.excavator.boot.authorization.constant.TokenConstants
import org.excavator.boot.authorization.manager.TokenManager
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

@Component
class AuthorizationInterceptor extends HandlerInterceptorAdapter {
  @Resource
  private[interceptor] val tokenManager:TokenManager = null

  @throws[Exception]
  override def preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean = {
    if (!handler.isInstanceOf[HandlerMethod]) return true

    val handlerMethod = handler.asInstanceOf[HandlerMethod]

    val method = handlerMethod.getMethod

    if (method.getAnnotation(classOf[Authorization]) != null) {
      val authorization = request.getHeader(TokenConstants.AUTHORIZATION)
      val token = tokenManager.getToken(authorization)
      if (tokenManager.checkToken(token)) {
        request.setAttribute(TokenConstants.CURRENT_USER_ID, token.getCustomerId)
        return true
      }
      else {
        val responseEntity = new ResponseEntity[String]("authorization failed", HttpStatus.UNAUTHORIZED)
        response.setContentType("application/json; charset=UTF-8")
        val printout = response.getWriter
        printout.print(JSON.toJSONString(responseEntity))
        printout.flush()
        return false
      }
    }
    true
  }
}