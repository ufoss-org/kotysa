/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MARIADB_JAVA_USER
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityMariadbTest :
    AbstractJdbcMariadbTest<JavaUserMariadbRepository>(),
    JavaEntityTest<MARIADB_JAVA_USER, JavaUserMariadbRepository, JdbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserMariadbRepository(connection)
}


class JavaUserMariadbRepository(connection: Connection) :
    JavaUserRepository<MARIADB_JAVA_USER>(connection.sqlClient(mariadbTables), MARIADB_JAVA_USER)
