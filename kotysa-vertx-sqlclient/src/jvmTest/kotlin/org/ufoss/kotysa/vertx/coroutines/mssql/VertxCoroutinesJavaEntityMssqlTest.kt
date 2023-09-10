/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class VertxCoroutinesJavaEntityMssqlTest :
    AbstractVertxCoroutinesMssqlTest<JavaUserMssqlRepository>(),
    CoroutinesJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
