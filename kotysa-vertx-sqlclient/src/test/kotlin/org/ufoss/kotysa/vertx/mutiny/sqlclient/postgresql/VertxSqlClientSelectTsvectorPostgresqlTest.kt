/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.vertx.mutiny.sqlclient.*

class VertxSqlClientSelectTsvectorPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<TsvectorPostgresqlRepository>(),
    MutinySelectTsvectorTest<TsvectorPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = TsvectorPostgresqlRepository(sqlClient)
}

class TsvectorPostgresqlRepository(sqlClient: PostgresqlMutinySqlClient) : MutinySelectTsvectorRepository(sqlClient)
