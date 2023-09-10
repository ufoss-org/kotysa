/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class VertxCoroutinesJavaEntityMariadbTest :
    AbstractVertxCoroutinesMariadbTest<JavaUserMariadbRepository>(),
    CoroutinesJavaEntityTest<MariadbJavaUsers, JavaUserMariadbRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = JavaUserMariadbRepository(sqlClient)
}

class JavaUserMariadbRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesJavaUserRepository<MariadbJavaUsers>(sqlClient, MariadbJavaUsers)
