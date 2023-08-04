/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorInheritanceTest

class R2DbcInheritanceMssqlTest : AbstractR2dbcMssqlTest<ReactorInheritanceMssqlRepository>(),
    ReactorInheritanceTest<MssqlInheriteds, ReactorInheritanceMssqlRepository, ReactorTransaction> {
    override val table = MssqlInheriteds

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ReactorInheritanceMssqlRepository(sqlClient)
}

class ReactorInheritanceMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorInheritanceRepository<MssqlInheriteds>(sqlClient, MssqlInheriteds)
