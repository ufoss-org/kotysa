/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class R2DbcInheritanceMysqlTest : AbstractR2dbcMysqlTest<ReactorInheritanceMysqlRepository>(),
    ReactorInheritanceTest<MysqlInheriteds, ReactorInheritanceMysqlRepository, ReactorTransaction> {
    override val table = MysqlInheriteds

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        ReactorInheritanceMysqlRepository(sqlClient)
}

class ReactorInheritanceMysqlRepository(sqlClient: MysqlReactorSqlClient) :
    ReactorInheritanceRepository<MysqlInheriteds>(sqlClient, MysqlInheriteds)
