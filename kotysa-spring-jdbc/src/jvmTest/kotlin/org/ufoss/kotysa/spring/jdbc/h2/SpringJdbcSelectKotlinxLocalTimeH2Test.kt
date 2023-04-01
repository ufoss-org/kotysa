/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimeH2Test : AbstractSpringJdbcH2Test<KotlinxLocalTimeRepositoryH2Select>(),
    SelectKotlinxLocalTimeTest<H2KotlinxLocalTimes, KotlinxLocalTimeRepositoryH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalTimeRepositoryH2Select(jdbcOperations)
}

class KotlinxLocalTimeRepositoryH2Select(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<H2KotlinxLocalTimes>(client.sqlClient(h2Tables), H2KotlinxLocalTimes)
