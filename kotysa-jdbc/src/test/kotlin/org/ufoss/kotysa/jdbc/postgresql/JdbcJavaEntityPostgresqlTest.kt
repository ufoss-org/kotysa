/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository

class JdbcJavaEntityPostgresqlTest : AbstractJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    JavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserPostgresqlRepository(sqlClient)
}


class JavaUserPostgresqlRepository(sqlClient: JdbcSqlClient) :
    JavaUserRepository<POSTGRESQL_JAVA_USER>(sqlClient, POSTGRESQL_JAVA_USER)
