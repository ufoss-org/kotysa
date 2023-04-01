/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityMysqlTest :
    AbstractSpringJdbcMysqlTest<JavaUserMysqlRepository>(),
    JavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = JavaUserMysqlRepository(jdbcOperations)
}


class JavaUserMysqlRepository(client: JdbcOperations) :
    JavaUserRepository<MysqlJavaUsers>(client.sqlClient(mysqlTables), MysqlJavaUsers)
