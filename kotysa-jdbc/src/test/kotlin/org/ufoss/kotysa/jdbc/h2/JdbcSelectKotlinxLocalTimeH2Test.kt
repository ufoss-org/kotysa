/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalTimes
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class JdbcSelectKotlinxLocalTimeH2Test : AbstractJdbcH2Test<KotlinxLocalTimeRepositoryH2Select>(),
    SelectKotlinxLocalTimeTest<H2KotlinxLocalTimes, KotlinxLocalTimeRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalTimeRepositoryH2Select(sqlClient)
}

class KotlinxLocalTimeRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalTimeRepository<H2KotlinxLocalTimes>(sqlClient, H2KotlinxLocalTimes)
