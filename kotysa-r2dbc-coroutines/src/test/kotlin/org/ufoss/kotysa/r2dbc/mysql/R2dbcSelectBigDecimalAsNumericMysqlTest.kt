/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class R2dbcSelectBigDecimalAsNumericMysqlTest : AbstractR2dbcMysqlTest<BigDecimalAsNumericRepositoryMysqlSelect>(),
    CoroutinesSelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalAsNumericRepositoryMysqlSelect(sqlClient)
}

class BigDecimalAsNumericRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)
