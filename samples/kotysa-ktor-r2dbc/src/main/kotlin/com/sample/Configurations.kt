package com.sample

import io.ktor.application.*
import io.ktor.serialization.*
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactories
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.kodein.di.*
import org.kodein.di.ktor.closestDI
import org.kodein.di.ktor.di
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransactionalOp
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.tables

fun Application.configuration() {
    di {
        import(dataModule)
    }
    // init DB
    val roleRepository by closestDI().instance<RoleRepository>()
    roleRepository.init()
    val userRepository by closestDI().instance<UserRepository>()
    userRepository.init()
}

private val h2Tables = tables().h2(ROLE, USER)

private val dataModule = DI.Module(name = "data") {
    bind<Connection>() with singleton {
        runBlocking {
            ConnectionFactories.get("r2dbc:h2:mem:///testdb;DB_CLOSE_DELAY=-1").create().awaitSingle()
        }
    }
    // create Kotysa SqlClient and TransactionalOp
    bind<CoroutinesSqlClient>() with provider { instance<Connection>().sqlClient(h2Tables) }
    bind<R2dbcTransactionalOp>() with provider { instance<Connection>().transactionalOp() }
    bind<RoleRepository>() with provider { RoleRepository(instance()) }
    bind<UserRepository>() with provider { UserRepository(instance(), instance()) }
    bind<Json>() with singleton { DefaultJson }
}   
