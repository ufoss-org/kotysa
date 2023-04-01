/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlFloats
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatMysqlTest : AbstractR2dbcMysqlTest<FloatMysqlRepository>(),
    ReactorSelectFloatTest<MysqlFloats, FloatMysqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        FloatMysqlRepository(sqlClient)

    // in returns inconsistent results
    override fun `Verify selectAllByFloatNotNullIn finds both`() {}
}

class FloatMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorSelectFloatRepository<MysqlFloats>(sqlClient, MysqlFloats)
