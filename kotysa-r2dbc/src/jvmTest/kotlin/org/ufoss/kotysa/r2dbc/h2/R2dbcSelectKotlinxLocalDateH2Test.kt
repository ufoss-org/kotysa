/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2KotlinxLocalDates
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectKotlinxLocalDateTest


class R2dbcSelectKotlinxLocalDateH2Test : AbstractR2dbcH2Test<KotlinxLocalDateRepositoryH2Select>(),
    CoroutinesSelectKotlinxLocalDateTest<H2KotlinxLocalDates, KotlinxLocalDateRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = KotlinxLocalDateRepositoryH2Select(sqlClient)
}

class KotlinxLocalDateRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectKotlinxLocalDateRepository<H2KotlinxLocalDates>(sqlClient, H2KotlinxLocalDates)
