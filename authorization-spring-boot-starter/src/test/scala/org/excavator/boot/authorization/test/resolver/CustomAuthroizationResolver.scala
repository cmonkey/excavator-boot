package org.excavator.boot.authorization.test.resolver

import javax.servlet.http.HttpServletResponse
import org.excavator.boot.authorization.interceptor.AuthorizationResolver
import org.excavator.boot.common.helper.JSONProxy
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Component

@Component("authorizationResolver")
class CustomAuthroizationResolver extends AuthorizationResolver{

  override def writeResponse(response: HttpServletResponse): Unit = {
    val responseEntity = new ResponseEntity[String]("", HttpStatus.OK)

    response.setContentType("application/json; charset=UTF-8")
    response.setStatus(HttpStatus.OK.value())

    val printout = response.getWriter

    printout.print(JSONProxy.toJSONString(responseEntity))

    printout.flush()
  }

}
