/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2LocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDateH2Test : AbstractJdbcH2Test<LocalDateRepositoryH2Select>(),
    SelectLocalDateTest<H2LocalDates, LocalDateRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateRepositoryH2Select(sqlClient)
}

class LocalDateRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<H2LocalDates>(sqlClient, H2LocalDates)
