/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectOrderByTest

class R2dbcSelectOrderByMysqlTest : AbstractR2dbcMysqlTest<OrderByRepositoryMysqlSelect>(),
    CoroutinesSelectOrderByTest<MysqlCustomers, OrderByRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = OrderByRepositoryMysqlSelect(sqlClient)
}

class OrderByRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectOrderByRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
