/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class VertxCoroutinesSelectLongMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectLongRepositoryMssqlSelect>(),
    CoroutinesSelectLongTest<MssqlLongs, SelectLongRepositoryMssqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectLongRepositoryMssqlSelect(sqlClient)
}


class SelectLongRepositoryMssqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLongRepository<MssqlLongs>(sqlClient, MssqlLongs)
