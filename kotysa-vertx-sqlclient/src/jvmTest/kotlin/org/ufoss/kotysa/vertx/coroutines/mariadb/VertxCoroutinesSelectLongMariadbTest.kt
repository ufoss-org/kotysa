/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class VertxCoroutinesSelectLongMariadbTest : AbstractVertxCoroutinesMariadbTest<SelectLongRepositoryMariadbSelect>(),
    CoroutinesSelectLongTest<MariadbLongs, SelectLongRepositoryMariadbSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectLongRepositoryMariadbSelect(sqlClient)
}


class SelectLongRepositoryMariadbSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLongRepository<MariadbLongs>(sqlClient, MariadbLongs)
