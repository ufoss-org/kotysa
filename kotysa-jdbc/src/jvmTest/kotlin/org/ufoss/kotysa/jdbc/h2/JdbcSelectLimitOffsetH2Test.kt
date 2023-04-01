/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Customers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class JdbcSelectLimitOffsetH2Test : AbstractJdbcH2Test<LimitOffsetRepositoryH2Select>(),
    SelectLimitOffsetTest<H2Customers, LimitOffsetRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LimitOffsetRepositoryH2Select(sqlClient)
}

class LimitOffsetRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectLimitOffsetRepository<H2Customers>(sqlClient, H2Customers)
