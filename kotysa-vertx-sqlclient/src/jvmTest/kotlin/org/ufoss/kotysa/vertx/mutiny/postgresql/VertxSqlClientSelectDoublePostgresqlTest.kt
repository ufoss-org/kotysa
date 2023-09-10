/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleTest

class VertxSqlClientSelectDoublePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<DoublePostgresqlRepository>(),
    MutinySelectDoubleTest<PostgresqlDoubles, DoublePostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) = DoublePostgresqlRepository(sqlClient)
}

class DoublePostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)
