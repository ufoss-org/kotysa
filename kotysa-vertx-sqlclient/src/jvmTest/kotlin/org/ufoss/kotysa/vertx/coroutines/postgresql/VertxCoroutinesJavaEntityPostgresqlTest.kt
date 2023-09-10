/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.postgresql

import org.ufoss.kotysa.vertx.PostgresqlCoroutinesVertxSqlClient
import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository


class VertxCoroutinesJavaEntityPostgresqlTest : AbstractVertxCoroutinesPostgresqlTest<JavaUserPostgresqlRepository>(),
    CoroutinesJavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: PostgresqlCoroutinesVertxSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesJavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
