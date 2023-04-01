/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMysqlTest : AbstractR2dbcMysqlTest<BigDecimalAsNumericMysqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MysqlBigDecimalAsNumerics, BigDecimalAsNumericMysqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        BigDecimalAsNumericMysqlRepository(sqlClient)
}

class BigDecimalAsNumericMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorSelectBigDecimalAsNumericRepository<MysqlBigDecimalAsNumerics>(sqlClient, MysqlBigDecimalAsNumerics)
