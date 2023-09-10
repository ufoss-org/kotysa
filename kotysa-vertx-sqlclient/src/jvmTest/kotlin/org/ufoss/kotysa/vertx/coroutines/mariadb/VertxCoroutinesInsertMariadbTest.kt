/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import io.vertx.mysqlclient.MySQLException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInsertTest

@Order(3)
class VertxCoroutinesInsertMariadbTest : AbstractVertxCoroutinesMariadbTest<RepositoryMariadbInsert>(),
    CoroutinesInsertTest<MariadbInts, MariadbLongs, MariadbCustomers, RepositoryMariadbInsert, Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = RepositoryMariadbInsert(sqlClient)
    override val exceptionClass = MySQLException::class.java
}

class RepositoryMariadbInsert(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInsertRepository<MariadbInts, MariadbLongs, MariadbCustomers>(
        sqlClient,
        MariadbInts,
        MariadbLongs,
        MariadbCustomers
    )
