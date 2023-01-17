/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class R2dbcSelectIntH2Test : AbstractR2dbcH2Test<SelectIntRepositoryH2Select>(),
    CoroutinesSelectIntTest<H2Ints, SelectIntRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntRepositoryH2Select(sqlClient)
}

class SelectIntRepositoryH2Select(sqlClient: R2dbcSqlClient) : CoroutinesSelectIntRepository<H2Ints>(sqlClient, H2Ints)
