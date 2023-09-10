/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTsvectorRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectTsvectorTest

class VertxSqlClientSelectTsvectorPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<TsvectorPostgresqlRepository>(),
    MutinySelectTsvectorTest<TsvectorPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = TsvectorPostgresqlRepository(sqlClient)
}

class TsvectorPostgresqlRepository(sqlClient: PostgresqlMutinySqlClient) : MutinySelectTsvectorRepository(sqlClient)
