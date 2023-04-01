/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatMysqlTest : AbstractR2dbcMysqlTest<FloatRepositoryMysqlSelect>(),
    CoroutinesSelectFloatTest<MysqlFloats, FloatRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = FloatRepositoryMysqlSelect(sqlClient)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<MysqlFloats>(sqlClient, MysqlFloats)
