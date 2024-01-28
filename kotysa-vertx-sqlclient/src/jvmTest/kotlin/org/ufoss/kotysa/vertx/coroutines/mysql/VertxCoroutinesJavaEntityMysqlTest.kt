/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class VertxCoroutinesJavaEntityMysqlTest :
    AbstractVertxCoroutinesMysqlTest<JavaUserMysqlRepository>(),
    CoroutinesJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = JavaUserMysqlRepository(sqlClient)
}

class JavaUserMysqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesJavaUserRepository<MysqlJavaUsers>(sqlClient, MysqlJavaUsers)
