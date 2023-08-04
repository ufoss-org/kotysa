/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectOrderByTest

class R2dbcSelectOrderByMysqlTest : AbstractR2dbcMysqlTest<OrderByRepositoryMysqlSelect>(),
    ReactorSelectOrderByTest<MysqlCustomers, OrderByRepositoryMysqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        OrderByRepositoryMysqlSelect(sqlClient)
}

class OrderByRepositoryMysqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectOrderByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
