/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumMysqlTest :
    AbstractVertxSqlClientMysqlTest<MinMaxAvgSumRepositoryMysqlSelect>(),
    MutinySelectMinMaxAvgSumTest<MysqlCustomers, MinMaxAvgSumRepositoryMysqlSelect> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = MinMaxAvgSumRepositoryMysqlSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMysqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
