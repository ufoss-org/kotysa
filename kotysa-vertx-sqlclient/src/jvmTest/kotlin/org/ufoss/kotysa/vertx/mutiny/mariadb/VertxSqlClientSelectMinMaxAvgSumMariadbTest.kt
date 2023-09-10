/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.ufoss.kotysa.test.MariadbCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumMariadbTest :
    AbstractVertxSqlClientMariadbTest<MinMaxAvgSumRepositoryMariadbSelect>(),
    MutinySelectMinMaxAvgSumTest<MariadbCustomers, MinMaxAvgSumRepositoryMariadbSelect> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = MinMaxAvgSumRepositoryMariadbSelect(sqlClient)
}

class MinMaxAvgSumRepositoryMariadbSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<MariadbCustomers>(sqlClient, MariadbCustomers)
