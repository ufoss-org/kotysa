/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import io.vertx.mysqlclient.MySQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class VertxCoroutinesInsertMysqlTest : AbstractVertxCoroutinesMysqlTest<RepositoryMysqlInsert>(),
    CoroutinesInsertTest<MysqlInts, MysqlLongs, MysqlCustomers, RepositoryMysqlInsert, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = RepositoryMysqlInsert(sqlClient)
    override val exceptionClass = MySQLException::class.java
}

class RepositoryMysqlInsert(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInsertRepository<MysqlInts, MysqlLongs, MysqlCustomers>(
        sqlClient,
        MysqlInts,
        MysqlLongs,
        MysqlCustomers
    )
