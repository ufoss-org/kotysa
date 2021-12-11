/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.POSTGRESQL_JAVA_USER
import org.ufoss.kotysa.test.postgresqlTables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityPostgresqlTest : AbstractJdbcPostgresqlTest<JavaUserPostgresqlRepository>(),
        JdbcJavaEntityTest<POSTGRESQL_JAVA_USER, JavaUserPostgresqlRepository> {
    override fun instantiateRepository(connection: Connection) = JavaUserPostgresqlRepository(connection)
}


class JavaUserPostgresqlRepository(connection: Connection)
    : JavaUserRepository<POSTGRESQL_JAVA_USER>(connection.sqlClient(postgresqlTables), POSTGRESQL_JAVA_USER)