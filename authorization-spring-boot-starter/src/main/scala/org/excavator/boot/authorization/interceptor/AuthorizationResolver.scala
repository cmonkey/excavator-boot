package org.excavator.boot.authorization.interceptor

import jakarta.servlet.http.HttpServletResponse
import org.excavator.boot.common.helper.JSONProxy
import org.springframework.http.{HttpStatus, ResponseEntity}

class AuthorizationResolver {

   def writeResponse(response:HttpServletResponse) = {
    val responseEntity = new ResponseEntity[String]("authorization failed", HttpStatus.UNAUTHORIZED)

    response.setContentType("application/json; charset=UTF-8")
    response.setStatus(HttpStatus.UNAUTHORIZED.value())

    val printout = response.getWriter

    printout.print(JSONProxy.toJSONString(responseEntity))

    printout.flush()
  }
}
