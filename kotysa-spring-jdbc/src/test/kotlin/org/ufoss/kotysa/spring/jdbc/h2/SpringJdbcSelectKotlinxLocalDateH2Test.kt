/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDates
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateH2Test : AbstractSpringJdbcH2Test<KotlinxLocalDateRepositoryH2Select>(),
    SelectKotlinxLocalDateTest<H2KotlinxLocalDates, KotlinxLocalDateRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<KotlinxLocalDateRepositoryH2Select>()
    override val repository = getContextRepository<KotlinxLocalDateRepositoryH2Select>()
}

class KotlinxLocalDateRepositoryH2Select(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<H2KotlinxLocalDates>(client.sqlClient(h2Tables), H2KotlinxLocalDates)
