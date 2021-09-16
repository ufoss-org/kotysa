package com.sample

import io.ktor.application.*
import org.h2.jdbcx.JdbcConnectionPool
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.kodein.di.provider
import org.ufoss.kotysa.SqlClient
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.tables
import javax.sql.DataSource

fun Application.configuration(profiles: List<String>) {
    di {
        import(dataModule(profiles))
    }
}

private val h2Tables = tables().h2(ROLE, USER)

private fun dataModule(profiles: List<String>) = DI.Module(name = "data") {
    bind<DataSource>() with instance(getDataSource(profiles))
    // create Kotysa SqlClient
    bind<SqlClient>() with provider { instance<DataSource>().connection.sqlClient(h2Tables) }
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance()) }
}

internal fun getDataSource(profiles: List<String>): DataSource =
    JdbcConnectionPool.create("jdbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1", "sa", "sa")
