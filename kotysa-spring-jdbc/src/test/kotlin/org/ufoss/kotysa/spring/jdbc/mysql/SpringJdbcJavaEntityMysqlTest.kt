/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.MYSQL_JAVA_USER
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.JdbcJavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityMysqlTest :
        AbstractSpringJdbcMysqlTest<JavaUserMysqlRepository>(), JdbcJavaEntityTest<MYSQL_JAVA_USER, JavaUserMysqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMysqlRepository>(resource)
    }

    override val repository: JavaUserMysqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMysqlRepository(client: JdbcOperations)
    : JavaUserRepository<MYSQL_JAVA_USER>(client.sqlClient(mysqlTables), MYSQL_JAVA_USER)
