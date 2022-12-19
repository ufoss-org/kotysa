/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalMysqlTest : AbstractR2dbcMysqlTest<BigDecimalRepositoryMysqlSelect>(),
    CoroutinesSelectBigDecimalTest<MysqlBigDecimals, BigDecimalRepositoryMysqlSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalRepositoryMysqlSelect(sqlClient)
}

class BigDecimalRepositoryMysqlSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)
