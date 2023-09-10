/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.oracle

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class VertxCoroutinesJavaEntityOracleTest : AbstractVertxCoroutinesOracleTest<JavaUserOracleRepository>(),
    CoroutinesJavaEntityTest<OracleJavaUsers, JavaUserOracleRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = JavaUserOracleRepository(sqlClient)
}

class JavaUserOracleRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesJavaUserRepository<OracleJavaUsers>(sqlClient, OracleJavaUsers)
