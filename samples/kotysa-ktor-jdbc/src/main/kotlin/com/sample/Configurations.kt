package com.sample

import io.ktor.application.*
import org.h2.jdbcx.JdbcConnectionPool
import org.kodein.di.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransactionalOp
import org.ufoss.kotysa.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.tables
import javax.sql.DataSource

fun Application.configuration(profiles: List<String>) {
    di {
        import(dataModule)
    }
    // init DB
    if (profiles.any { profile -> "dev" == profile || "test" == profile }) {
        val roleRepository by closestDI().instance<RoleRepository>()
        roleRepository.init()
        val userRepository by closestDI().instance<UserRepository>()
        userRepository.init()
    }
}

private val h2Tables = tables().h2(ROLE, USER)

private val dataModule = DI.Module(name = "data") {
    bind<DataSource>() with singleton {
        JdbcConnectionPool.create("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1", "sa", "sa")
    }
    // create Kotysa SqlClient and TransactionalOp
    bind<SqlClient>() with provider { instance<DataSource>().connection.sqlClient(h2Tables) }
    bind<JdbcTransactionalOp>() with provider { instance<DataSource>().connection.transactionalOp() }
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance(), instance()) }
}   
