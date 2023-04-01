/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritanceMssqlTest : AbstractR2dbcMssqlTest<InheritanceMssqlRepository>(),
    CoroutinesInheritanceTest<MssqlInheriteds, InheritanceMssqlRepository, R2dbcTransaction> {
    override val table = MssqlInheriteds
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceMssqlRepository(sqlClient)
}

class InheritanceMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<MssqlInheriteds>(sqlClient, MssqlInheriteds)
