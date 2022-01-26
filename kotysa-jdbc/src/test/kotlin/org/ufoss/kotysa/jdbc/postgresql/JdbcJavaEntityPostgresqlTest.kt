/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityPostgresqlTest : AbstractJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
    JavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository, JdbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserPostgresqlRepository(connection)
}


class JavaUserPostgresqlRepository(connection: Connection) :
    JavaUserRepository<POSTGRESQL_JAVA_USER>(connection.sqlClient(postgresqlTables), POSTGRESQL_JAVA_USER)
