/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeTest

class SpringJdbcSelectKotlinxLocalDateTimeH2Test : AbstractSpringJdbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>(),
    SelectKotlinxLocalDateTimeTest<H2KotlinxLocalDateTimes, KotlinxLocalDateTimeRepositoryH2Select,
            SpringJdbcTransaction> {
    override val context = startContext<KotlinxLocalDateTimeRepositoryH2Select>()
    override val repository = getContextRepository<KotlinxLocalDateTimeRepositoryH2Select>()
}

class KotlinxLocalDateTimeRepositoryH2Select(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeRepository<H2KotlinxLocalDateTimes>(client.sqlClient(h2Tables), H2KotlinxLocalDateTimes)
