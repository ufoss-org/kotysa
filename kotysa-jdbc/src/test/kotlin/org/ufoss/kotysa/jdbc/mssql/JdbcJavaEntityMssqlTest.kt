/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.MSSQL_JAVA_USER
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityMssqlTest :
        AbstractJdbcMssqlTest<JavaUserMssqlRepository>(), JavaEntityTest<MSSQL_JAVA_USER, JavaUserMssqlRepository> {
    override fun instantiateRepository(connection: Connection) = JavaUserMssqlRepository(connection)
}


class JavaUserMssqlRepository(connection: Connection)
    : JavaUserRepository<MSSQL_JAVA_USER>(connection.sqlClient(mssqlTables), MSSQL_JAVA_USER)
