package org.excavator.boot.authorization.test.resolver

import javax.annotation.Resource
import org.excavator.boot.authorization.annotation.CurrentUser
import org.excavator.boot.authorization.constant.TokenConstants
import org.excavator.boot.authorization.manager.TokenManager
import org.excavator.boot.authorization.test.entity.Customer
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.{NativeWebRequest, RequestAttributes}
import org.springframework.web.method.support.{HandlerMethodArgumentResolver, ModelAndViewContainer}
import org.springframework.web.multipart.support.MissingServletRequestPartException

@Component
class CurrentUserMethodArgumentResolver extends HandlerMethodArgumentResolver{

  val logger = LoggerFactory.getLogger(classOf[CurrentUserMethodArgumentResolver])

  @Resource
  val tokenManager: TokenManager = null

  override def supportsParameter(parameter: MethodParameter): Boolean =
    parameter.getParameterType.isAssignableFrom(classOf[Customer]) && parameter.hasParameterAnnotation(classOf[CurrentUser])

  override def resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory): AnyRef = {
    val customerId = webRequest.getAttribute(TokenConstants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST).asInstanceOf[Long]

    logger.info("resolveArgument param customerId = {}", customerId)

    if (null != customerId) {
      val customer = new Customer(10, "cmonkey", "12345678911")
      if (null != customer) return customer
      else {
        val token = webRequest.getAttribute(TokenConstants.AUTHORIZATION, RequestAttributes.SCOPE_REQUEST).asInstanceOf[String]
        tokenManager.deleteToken(token)
        throw new MissingServletRequestPartException(HttpStatus.UNAUTHORIZED.name)
      }
    }

    throw new MissingServletRequestPartException(TokenConstants.CURRENT_USER_ID)
  }
}
