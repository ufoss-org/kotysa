/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritanceMssqlTest : AbstractJdbcMssqlTest<InheritanceMssqlRepository>(),
    InheritanceTest<MssqlInheriteds, InheritanceMssqlRepository, JdbcTransaction> {
    override val table = MssqlInheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceMssqlRepository(sqlClient)
}

class InheritanceMssqlRepository(sqlClient: JdbcSqlClient) :
    InheritanceRepository<MssqlInheriteds>(sqlClient, MssqlInheriteds)
