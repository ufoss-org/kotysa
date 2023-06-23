/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumMssqlTest :
    AbstractVertxSqlClientMssqlTest<MinMaxAvgSumRepositoryMssqlSelect>(),
    MutinySelectMinMaxAvgSumTest<MssqlCustomers, MinMaxAvgSumRepositoryMssqlSelect> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = MinMaxAvgSumRepositoryMssqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<MssqlCustomers>(sqlClient, MssqlCustomers)
