package com.sample

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.r2dbc.r2dbc
import org.springframework.fu.kofu.webflux.webFlux
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables

private val h2Tables = tables().h2(ROLE, USER)

val dataConfig = configuration {
    beans {
        bean { ref<DatabaseClient>().sqlClient(h2Tables) }
        bean<RoleRepository>()
        bean<UserRepository>()
    }
    if (profiles.any { profile -> "dev" == profile || "test" == profile }) {
        listener<ApplicationReadyEvent> {
            ref<RoleRepository>().init()
            ref<UserRepository>().init()
        }
    }
    r2dbc {
        url = "r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
    }
}

val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean(::routes)
    }
    webFlux {
        port = if (profiles.any { profile -> "dev" == profile || "test" == profile }) 8181 else 8080
        codecs {
            jackson()
        }
    }
}
