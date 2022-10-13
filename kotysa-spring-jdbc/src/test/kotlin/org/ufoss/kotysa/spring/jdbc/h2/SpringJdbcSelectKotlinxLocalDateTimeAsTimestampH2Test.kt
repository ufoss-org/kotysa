/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class SpringJdbcSelectKotlinxLocalDateTimeAsTimestampH2Test :
    AbstractSpringJdbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<H2KotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()
    override val repository = getContextRepository<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()
}

class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(client: JdbcOperations) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<H2KotlinxLocalDateTimeAsTimestamps>(
        client.sqlClient(h2Tables),
        H2KotlinxLocalDateTimeAsTimestamps
    )
