package com.sample

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.jdbc.DataSourceType
import org.springframework.fu.kofu.jdbc.jdbc
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.tables

private val h2Tables = tables().h2(ROLE, USER)

val dataConfig = configuration {
    beans {
        // create Kotysa SqlClient
        bean { ref<JdbcOperations>().sqlClient(h2Tables) }
        bean<RoleRepository>()
        bean<UserRepository>()
    }
    if (profiles.any { profile -> "dev" == profile || "test" == profile }) {
        listener<ApplicationReadyEvent> {
            ref<RoleRepository>().init()
            ref<UserRepository>().init()
        }
    }
        jdbc(DataSourceType.Embedded) {
            url = "jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1"
        }
}

val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean(::routes)
    }
    webMvc {
        port = if (profiles.any { profile -> "dev" == profile || "test" == profile }) 8181 else 8080
        converters {
            jackson()
        }
    }
}
