/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityH2Test :
        AbstractJdbcH2Test<JavaUserH2Repository>(), JdbcJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository> {
    override fun instantiateRepository(connection: Connection) = JavaUserH2Repository(connection)
}


class JavaUserH2Repository(connection: Connection)
    : JavaUserRepository<H2_JAVA_USER>(connection.sqlClient(h2Tables), H2_JAVA_USER)
