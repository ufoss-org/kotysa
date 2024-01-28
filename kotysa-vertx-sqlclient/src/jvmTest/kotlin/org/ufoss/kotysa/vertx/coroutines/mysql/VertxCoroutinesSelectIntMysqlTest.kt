/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectIntTest

@Order(1)
class VertxCoroutinesSelectIntMysqlTest : AbstractVertxCoroutinesMysqlTest<SelectIntRepositoryMysqlSelect>(),
    CoroutinesSelectIntTest<MysqlInts, SelectIntRepositoryMysqlSelect, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectIntRepositoryMysqlSelect(sqlClient)
}

class SelectIntRepositoryMysqlSelect(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectIntRepository<MysqlInts>(sqlClient, MysqlInts)
