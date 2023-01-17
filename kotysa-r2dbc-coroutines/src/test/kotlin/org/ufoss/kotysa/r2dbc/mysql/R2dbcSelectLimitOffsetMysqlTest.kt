/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlCustomers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLimitOffsetTest

class R2dbcSelectLimitOffsetMysqlTest : AbstractR2dbcMysqlTest<LimitOffsetRepositoryMysqlSelect>(),
    CoroutinesSelectLimitOffsetTest<MysqlCustomers, LimitOffsetRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LimitOffsetRepositoryMysqlSelect(sqlClient)
}

class LimitOffsetRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLimitOffsetRepository<MysqlCustomers>(sqlClient, MysqlCustomers)
