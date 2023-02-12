/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoubleMysqlTest : AbstractR2dbcMysqlTest<DoubleRepositoryMysqlSelect>(),
    CoroutinesSelectDoubleTest<MysqlDoubles, DoubleRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = DoubleRepositoryMysqlSelect(sqlClient)
}

class DoubleRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)
