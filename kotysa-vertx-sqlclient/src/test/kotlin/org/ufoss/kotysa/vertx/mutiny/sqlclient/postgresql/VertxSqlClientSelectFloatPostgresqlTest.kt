/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<FloatPostgresqlRepository>(),
    MutinySelectFloatTest<PostgresqlFloats, FloatPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = FloatPostgresqlRepository(sqlClient)
}

class FloatPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)
