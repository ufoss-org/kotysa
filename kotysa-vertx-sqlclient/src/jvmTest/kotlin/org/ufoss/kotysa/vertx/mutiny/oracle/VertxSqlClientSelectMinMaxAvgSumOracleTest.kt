/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumOracleTest :
    AbstractVertxSqlClientOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    MutinySelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)
