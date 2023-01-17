/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import org.ufoss.kotysa.test.OracleCustomers
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOrderByRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectOrderByTest

class VertxSqlClientSelectOrderByOracleTest : AbstractVertxSqlClientOracleTest<OrderByRepositoryOracleSelect>(),
    MutinySelectOrderByTest<OracleCustomers, OrderByRepositoryOracleSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = OrderByRepositoryOracleSelect(sqlClient)
}

class OrderByRepositoryOracleSelect(sqlClient: VertxSqlClient) :
    MutinySelectOrderByRepository<OracleCustomers>(sqlClient, OracleCustomers)
