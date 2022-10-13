/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class JdbcSelectKotlinxLocalDateH2Test : AbstractJdbcH2Test<KotlinxLocalDateRepositoryH2Select>(),
    SelectKotlinxLocalDateTest<H2KotlinxLocalDates, KotlinxLocalDateRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateRepository<H2KotlinxLocalDates>(sqlClient, H2KotlinxLocalDates)
