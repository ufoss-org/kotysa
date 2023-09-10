/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.postgresql

import org.ufoss.kotysa.test.PostgresqlCustomers
import org.ufoss.kotysa.vertx.PostgresqlMutinyVertxSqlClient
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumPostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<MinMaxAvgSumRepositoryPostgresqlSelect>(),
    MutinySelectMinMaxAvgSumTest<PostgresqlCustomers, MinMaxAvgSumRepositoryPostgresqlSelect> {

    override fun instantiateRepository(sqlClient: PostgresqlMutinyVertxSqlClient) =
        MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryPostgresqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<PostgresqlCustomers>(sqlClient, PostgresqlCustomers)
