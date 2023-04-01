/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlBigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMysqlTest : AbstractR2dbcMysqlTest<BigDecimalMysqlRepository>(),
    ReactorSelectBigDecimalTest<MysqlBigDecimals, BigDecimalMysqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        BigDecimalMysqlRepository(sqlClient)
}

class BigDecimalMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorSelectBigDecimalRepository<MysqlBigDecimals>(sqlClient, MysqlBigDecimals)
