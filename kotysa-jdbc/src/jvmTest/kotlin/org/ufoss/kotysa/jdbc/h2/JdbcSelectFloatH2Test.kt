/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Floats
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectFloatTest

class JdbcSelectFloatH2Test : AbstractJdbcH2Test<SelectFloatRepositoryH2Select>(),
    SelectFloatTest<H2Floats, SelectFloatRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectFloatRepositoryH2Select(sqlClient)
}

class SelectFloatRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectFloatRepository<H2Floats>(sqlClient, H2Floats)
