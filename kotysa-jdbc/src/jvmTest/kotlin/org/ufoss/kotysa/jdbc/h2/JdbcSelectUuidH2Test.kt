/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Uuids
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class JdbcSelectUuidH2Test : AbstractJdbcH2Test<UuidRepositoryH2Select>(),
    SelectUuidTest<H2Uuids, UuidRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UuidRepositoryH2Select(sqlClient)
}

class UuidRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectUuidRepository<H2Uuids>(sqlClient, H2Uuids)
