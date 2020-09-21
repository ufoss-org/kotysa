package com.sample

import org.springframework.fu.kofu.reactiveWebApplication
import org.springframework.fu.kofu.webApplication

val app = webApplication {
    enable(dataConfig)
    enable(webConfig)
}

fun main() {
    app.run()
}
