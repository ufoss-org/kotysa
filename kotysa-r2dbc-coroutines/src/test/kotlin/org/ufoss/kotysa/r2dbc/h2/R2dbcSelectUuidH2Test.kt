/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Uuids
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectUuidTest

class R2dbcSelectUuidH2Test : AbstractR2dbcH2Test<UuidRepositoryH2Select>(),
    CoroutinesSelectUuidTest<H2Uuids, UuidRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UuidRepositoryH2Select(sqlClient)
}

class UuidRepositoryH2Select(sqlClient: R2dbcSqlClient) : CoroutinesSelectUuidRepository<H2Uuids>(sqlClient, H2Uuids)
