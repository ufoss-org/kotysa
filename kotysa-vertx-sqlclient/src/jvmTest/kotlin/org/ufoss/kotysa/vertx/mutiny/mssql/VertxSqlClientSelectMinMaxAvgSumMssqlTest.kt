/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumMssqlTest :
    AbstractVertxSqlClientMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    MutinySelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
