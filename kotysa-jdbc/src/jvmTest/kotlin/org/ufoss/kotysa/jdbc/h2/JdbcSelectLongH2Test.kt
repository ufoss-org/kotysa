/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class JdbcSelectLongH2Test : AbstractJdbcH2Test<SelectLongRepositoryH2Select>(),
    SelectLongTest<H2Longs, SelectLongRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectLongRepositoryH2Select(sqlClient)
}

class SelectLongRepositoryH2Select(sqlClient: JdbcSqlClient) : SelectLongRepository<H2Longs>(sqlClient, H2Longs)
