/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import io.r2dbc.spi.Connection
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MYSQL_JAVA_USER
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository


class R2dbcJavaEntityMysqlTest :
    AbstractR2dbcMysqlTest<JavaUserMysqlRepository>(),
    CoroutinesJavaEntityTest<MYSQL_JAVA_USER, JavaUserMysqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserMysqlRepository(connection)
}


class JavaUserMysqlRepository(connection: Connection) :
    CoroutinesJavaUserRepository<MYSQL_JAVA_USER>(connection.sqlClient(mysqlTables), MYSQL_JAVA_USER)
