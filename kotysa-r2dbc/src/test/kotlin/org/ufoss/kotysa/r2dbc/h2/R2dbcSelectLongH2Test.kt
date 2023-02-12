/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class R2dbcSelectLongH2Test : AbstractR2dbcH2Test<SelectLongRepositoryH2Select>(),
    CoroutinesSelectLongTest<H2Longs, SelectLongRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectLongRepositoryH2Select(sqlClient)
}


class SelectLongRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLongRepository<H2Longs>(sqlClient, H2Longs)
