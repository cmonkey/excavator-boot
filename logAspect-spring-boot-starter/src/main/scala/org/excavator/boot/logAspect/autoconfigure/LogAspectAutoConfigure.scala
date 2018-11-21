package org.excavator.boot.logAspcet.autoconfigure

import org.excavator.boot.logAspcet.aspect.WebLogAspect
import org.springframework.context.annotation.{Configuration, Import}

@Configuration
@Import(Array(classOf[WebLogAspect]))
class LogAspectAutoConfigure {

}
