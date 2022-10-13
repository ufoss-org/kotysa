/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class JdbcSelectIntH2Test : AbstractJdbcH2Test<SelectIntRepositoryH2Select>(),
    SelectIntTest<H2Ints, SelectIntRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectIntRepositoryH2Select(sqlClient)
}

class SelectIntRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectIntRepository<H2Ints>(sqlClient, H2Ints)
