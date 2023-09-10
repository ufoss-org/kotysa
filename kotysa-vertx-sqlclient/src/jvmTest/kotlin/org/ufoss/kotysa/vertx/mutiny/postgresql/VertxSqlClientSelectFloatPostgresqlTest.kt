/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlFloats
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<FloatPostgresqlRepository>(),
    MutinySelectFloatTest<PostgresqlFloats, FloatPostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = FloatPostgresqlRepository(sqlClient)
}

class FloatPostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<PostgresqlFloats>(sqlClient, PostgresqlFloats)
