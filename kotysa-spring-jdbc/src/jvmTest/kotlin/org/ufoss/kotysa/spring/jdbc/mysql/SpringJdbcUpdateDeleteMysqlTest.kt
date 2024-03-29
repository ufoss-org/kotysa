/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlUpdateDelete>(),
    UpdateDeleteTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies, UserRepositorySpringJdbcMysqlUpdateDelete,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMysqlUpdateDelete(jdbcOperations)
}

class UserRepositorySpringJdbcMysqlUpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        client.sqlClient(mysqlTables),
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
