/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Doubles
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDoubleTest

class JdbcSelectDoubleH2Test : AbstractJdbcH2Test<SelectDoubleRepositoryH2Select>(),
    SelectDoubleTest<H2Doubles, SelectDoubleRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectDoubleRepositoryH2Select(sqlClient)
}

class SelectDoubleRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectDoubleRepository<H2Doubles>(sqlClient, H2Doubles)
