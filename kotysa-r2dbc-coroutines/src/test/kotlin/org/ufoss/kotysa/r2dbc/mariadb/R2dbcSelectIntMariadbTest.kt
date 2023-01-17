/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class R2dbcSelectIntMariadbTest : AbstractR2dbcMariadbTest<SelectIntRepositoryMariadbSelect>(),
    CoroutinesSelectIntTest<MariadbInts, SelectIntRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectIntRepositoryMariadbSelect(sqlClient)
}

class SelectIntRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectIntRepository<MariadbInts>(sqlClient, MariadbInts)
