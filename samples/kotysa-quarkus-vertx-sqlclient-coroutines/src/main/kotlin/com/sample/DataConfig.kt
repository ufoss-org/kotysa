package com.sample

import io.vertx.pgclient.PgPool
import org.ufoss.kotysa.tables
import jakarta.enterprise.inject.Produces
import org.ufoss.kotysa.vertx.coSqlClient

private val postgreslTables = tables().postgresql(Roles, Users)

class DataConfig(private val dbClient: PgPool) {

    @Produces
    fun sqlClient() = dbClient.coSqlClient(postgreslTables)
}