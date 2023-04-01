/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Inheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritanceH2Test : AbstractJdbcH2Test<InheritanceH2Repository>(),
    InheritanceTest<H2Inheriteds, InheritanceH2Repository, JdbcTransaction> {
    override val table = H2Inheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceH2Repository(sqlClient)
}

class InheritanceH2Repository(sqlClient: JdbcSqlClient) : InheritanceRepository<H2Inheriteds>(sqlClient, H2Inheriteds)
