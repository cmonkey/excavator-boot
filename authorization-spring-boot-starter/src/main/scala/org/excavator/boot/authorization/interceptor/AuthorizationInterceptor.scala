package org.excavator.boot.authorization.interceptor


import javax.annotation.Resource
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.excavator.boot.authorization.annotation.Authorization
import org.excavator.boot.authorization.constant.TokenConstants
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.common.helper.JSONProxy
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
    var r = true
    if (!handler.isInstanceOf[HandlerMethod]) {
      r = true
    }else{
      val handlerMethod = handler.asInstanceOf[HandlerMethod]

      val method = handlerMethod.getMethod

      if (method.getAnnotation(classOf[Authorization]) != null) {

        val authorization = request.getHeader(TokenConstants.AUTHORIZATION)

        val tokenOpt = tokenManager.getToken(authorization)

        if(tokenOpt.isPresent){
          val token = tokenOpt.get()
          if(tokenManager.checkToken(token)){
            request.setAttribute(TokenConstants.CURRENT_USER_ID, token.getCustomerId)
            request.setAttribute(TokenConstants.AUTHORIZATION, authorization)
            r = true
          }else{
            writeResponse(response)
            r = false
          }
        }else{
          writeResponse(response)
          r = false
        }
      }else{
        r = true
      }
    }

    r
  }

  def writeResponse(response:HttpServletResponse) = {
    val responseEntity = new ResponseEntity[String]("authorization failed", HttpStatus.UNAUTHORIZED)

    response.setContentType("application/json; charset=UTF-8")
    response.setStatus(HttpStatus.UNAUTHORIZED.value())

    val printout = response.getWriter

    printout.print(JSONProxy.toJSONString(responseEntity))

    printout.flush()
  }
}
