/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.MYSQL_JAVA_USER
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository
import java.sql.Connection


class JdbcJavaEntityMysqlTest :
        AbstractJdbcMysqlTest<JavaUserMysqlRepository>(), JavaEntityTest<MYSQL_JAVA_USER, JavaUserMysqlRepository> {
    override fun instantiateRepository(connection: Connection) = JavaUserMysqlRepository(connection)
}


class JavaUserMysqlRepository(connection: Connection)
    : JavaUserRepository<MYSQL_JAVA_USER>(connection.sqlClient(mysqlTables), MYSQL_JAVA_USER)
