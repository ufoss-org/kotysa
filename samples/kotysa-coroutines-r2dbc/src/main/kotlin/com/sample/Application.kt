package com.sample

import org.springframework.fu.kofu.reactiveWebApplication

val app = reactiveWebApplication {
    enable(dataConfig)
    enable(webConfig)
}

fun main() {
    app.run()
}
