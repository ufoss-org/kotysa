/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlDoubles
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*

class VertxSqlClientSelectDoublePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<DoublePostgresqlRepository>(),
    MutinySelectDoubleTest<PostgresqlDoubles, DoublePostgresqlRepository> {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = DoublePostgresqlRepository(sqlClient)
}

class DoublePostgresqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<PostgresqlDoubles>(sqlClient, PostgresqlDoubles)
