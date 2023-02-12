/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class R2dbcSelectLongMariadbTest : AbstractR2dbcMariadbTest<SelectLongRepositoryMariadbSelect>(),
    CoroutinesSelectLongTest<MariadbLongs, SelectLongRepositoryMariadbSelect, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectLongRepositoryMariadbSelect(sqlClient)
}


class SelectLongRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectLongRepository<MariadbLongs>(sqlClient, MariadbLongs)
