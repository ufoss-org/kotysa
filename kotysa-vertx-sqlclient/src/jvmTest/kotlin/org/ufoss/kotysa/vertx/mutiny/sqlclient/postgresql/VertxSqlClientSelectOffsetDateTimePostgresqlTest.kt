/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOffsetDateTimeRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOffsetDateTimeTest

class VertxSqlClientSelectOffsetDateTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>(),
    MutinySelectOffsetDateTimeTest<PostgresqlOffsetDateTimes, OffsetDateTimeRepositoryPostgresqlSelect> {
    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) =
        OffsetDateTimeRepositoryPostgresqlSelect(sqlClient)
}

class OffsetDateTimeRepositoryPostgresqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectOffsetDateTimeRepository<PostgresqlOffsetDateTimes>(sqlClient, PostgresqlOffsetDateTimes)
