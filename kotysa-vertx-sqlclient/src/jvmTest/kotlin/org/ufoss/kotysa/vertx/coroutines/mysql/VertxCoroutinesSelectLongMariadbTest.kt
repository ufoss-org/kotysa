/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectLongTest

@Order(2)
class VertxCoroutinesSelectLongMysqlTest : AbstractVertxCoroutinesMysqlTest<SelectLongRepositoryMysqlSelect>(),
    CoroutinesSelectLongTest<MysqlLongs, SelectLongRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectLongRepositoryMysqlSelect(sqlClient)
}


class SelectLongRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectLongRepository<MysqlLongs>(sqlClient, MysqlLongs)
