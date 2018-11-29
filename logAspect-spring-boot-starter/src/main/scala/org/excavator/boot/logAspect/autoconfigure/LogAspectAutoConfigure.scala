package org.excavator.boot.logAspect.autoconfigure

import org.excavator.boot.logAspect.aspect.WebLogAspect
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.{Configuration, Import}

@Configuration
@ConditionalOnProperty(name = Array("excavator.logAspect.enabled"), havingValue = "true")
@Import(Array(classOf[WebLogAspect]))
class LogAspectAutoConfigure {

}
