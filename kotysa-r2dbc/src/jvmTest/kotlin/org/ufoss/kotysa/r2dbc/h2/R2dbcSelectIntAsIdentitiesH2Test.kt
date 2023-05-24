/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntAsIdentitiesTest

@Order(1)
class R2dbcSelectIntAsIdentitiesH2Test : AbstractR2dbcH2Test<SelectIntAsIdentitiesRepositoryH2Select>(),
    CoroutinesSelectIntAsIdentitiesTest<H2IntAsIdentities, SelectIntAsIdentitiesRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntAsIdentitiesRepositoryH2Select(sqlClient)
}

class SelectIntAsIdentitiesRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntAsIdentitiesRepository<H2IntAsIdentities>(sqlClient, H2IntAsIdentities)
