/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MYSQL_JAVA_USER
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository

class JdbcJavaEntityMysqlTest :
    AbstractJdbcMysqlTest<JavaUserMysqlRepository>(),
    JavaEntityTest<MYSQL_JAVA_USER, JavaUserMysqlRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserMysqlRepository(sqlClient)
}


class JavaUserMysqlRepository(sqlClient: JdbcSqlClient) :
    JavaUserRepository<MYSQL_JAVA_USER>(sqlClient, MYSQL_JAVA_USER)
