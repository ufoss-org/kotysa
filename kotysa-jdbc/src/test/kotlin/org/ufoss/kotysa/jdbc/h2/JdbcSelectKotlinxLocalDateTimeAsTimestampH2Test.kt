/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDateTimeAsTimestamps
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTimeAsTimestampTest

class JdbcSelectKotlinxLocalDateTimeAsTimestampH2Test :
    AbstractJdbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>(),
    SelectKotlinxLocalDateTimeAsTimestampTest<H2KotlinxLocalDateTimeAsTimestamps,
            KotlinxLocalDateTimeAsTimestampRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectKotlinxLocalDateTimeAsTimestampRepository<H2KotlinxLocalDateTimeAsTimestamps>(
        sqlClient,
        H2KotlinxLocalDateTimeAsTimestamps
    )
