/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.junit.jupiter.api.BeforeAll
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.MysqlJavaUsers
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import org.ufoss.kotysa.test.mysqlTables


class R2DbcJavaEntityMysqlTest :
        AbstractR2dbcMysqlTest<JavaUserMysqlRepository>(),
    R2dbcJavaEntityTest<MysqlJavaUsers, JavaUserMysqlRepository> {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<JavaUserMysqlRepository>(resource)
    }

    override val repository: JavaUserMysqlRepository by lazy {
        getContextRepository()
    }
}


class JavaUserMysqlRepository(client: DatabaseClient)
    : R2dbcJavaUserRepository<MysqlJavaUsers>(client.sqlClient(mysqlTables), MysqlJavaUsers)
