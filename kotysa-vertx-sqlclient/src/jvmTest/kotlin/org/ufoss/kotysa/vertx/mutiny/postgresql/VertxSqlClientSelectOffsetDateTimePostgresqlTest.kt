/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOffsetDateTimeTest

class VertxSqlClientSelectOffsetDateTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    MutinySelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        OffsetDateTimeRepositoryPostgresqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(sqlClient, PostgresqlOffsetDateTimes)
