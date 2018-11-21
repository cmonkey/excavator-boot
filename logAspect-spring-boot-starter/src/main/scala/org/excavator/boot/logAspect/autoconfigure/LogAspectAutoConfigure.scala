package org.excavator.boot.logAspect.autoconfigure

import org.excavator.boot.logAspect.aspect.WebLogAspect
import org.springframework.context.annotation.{Configuration, Import}

@Configuration
@Import(Array(classOf[WebLogAspect]))
class LogAspectAutoConfigure {

}
