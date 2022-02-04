/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityMysqlTest :
    AbstractSpringJdbcMysqlTest<JavaUserMysqlRepository>(),
    JavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository, SpringJdbcTransaction> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMysqlRepository>(resource)
    }

    override val repository: JavaUserMysqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMysqlRepository(client: JdbcOperations) :
    JavaUserRepository<MysqlJavaUsers>(client.sqlClient(mysqlTables), MysqlJavaUsers)
