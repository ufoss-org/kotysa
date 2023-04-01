/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlDoubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMysqlTest : AbstractR2dbcMysqlTest<DoubleMysqlRepository>(),
    ReactorSelectDoubleTest<MysqlDoubles, DoubleMysqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        DoubleMysqlRepository(sqlClient)
}

class DoubleMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorSelectDoubleRepository<MysqlDoubles>(sqlClient, MysqlDoubles)
