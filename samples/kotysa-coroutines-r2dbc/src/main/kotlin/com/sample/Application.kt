package com.sample

import org.springframework.boot.WebApplicationType
import org.springframework.fu.kofu.application

val app = application(WebApplicationType.REACTIVE) {
    enable(dataConfig)
    enable(webConfig)
}

fun main() {
    app.run()
}
