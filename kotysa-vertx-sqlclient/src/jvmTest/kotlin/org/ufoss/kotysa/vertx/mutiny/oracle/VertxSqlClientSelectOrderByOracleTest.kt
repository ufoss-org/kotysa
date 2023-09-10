/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOrderByRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectOrderByTest

class VertxSqlClientSelectOrderByOracleTest : AbstractVertxSqlClientOracleTest<OrderByRepositoryOracleSelect>(),
    MutinySelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = OrderByRepositoryOracleSelect(sqlClient)
}

class OrderByRepositoryOracleSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)
