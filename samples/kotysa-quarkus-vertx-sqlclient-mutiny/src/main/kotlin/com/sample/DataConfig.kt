package com.sample

import io.vertx.mutiny.pgclient.PgPool
import org.ufoss.kotysa.tables
import org.ufoss.kotysa.vertx.sqlClient
import jakarta.enterprise.inject.Produces

private val postgreslTables = tables().postgresql(Roles, Users)

class DataConfig(private val dbClient: PgPool) {

    @Produces
    fun sqlClient() = dbClient.sqlClient(postgreslTables)
}