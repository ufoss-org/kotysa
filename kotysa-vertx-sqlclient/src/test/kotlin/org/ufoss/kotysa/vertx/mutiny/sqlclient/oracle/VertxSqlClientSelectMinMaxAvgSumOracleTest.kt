/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectMinMaxAvgSumRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectMinMaxAvgSumTest

class VertxSqlClientSelectMinMaxAvgSumOracleTest :
    AbstractVertxSqlClientOracleTest<MinMaxAvgSumRepositoryOracleSelect>(),
    MutinySelectMinMaxAvgSumTest<OracleCustomers, MinMaxAvgSumRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = MinMaxAvgSumRepositoryOracleSelect(sqlClient)
}

class MinMaxAvgSumRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectMinMaxAvgSumRepository<OracleCustomers>(sqlClient, OracleCustomers)
