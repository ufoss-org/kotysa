/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.PostgresqlJavaUsers
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository

class JdbcJavaEntityPostgresqlTest : AbstractJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    JavaEntityTest<PostgresqlJavaUsers, JavaUserPostgresqlRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: JdbcSqlClient) :
    JavaUserRepository<PostgresqlJavaUsers>(sqlClient, PostgresqlJavaUsers)
