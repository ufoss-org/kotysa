/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class JdbcSelectKotlinxLocalDateTimeH2Test : AbstractJdbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>(),
    SelectKotlinxLocalDateTimeTest<H2KotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateTimeRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeRepository<H2KotlinxLocalDateTimes>(sqlClient, H2KotlinxLocalDateTimes)
