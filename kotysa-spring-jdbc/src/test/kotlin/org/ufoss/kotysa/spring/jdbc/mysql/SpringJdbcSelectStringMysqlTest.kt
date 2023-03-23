/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectString>(),
    SelectStringTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositorySpringJdbcMysqlSelectString,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMysqlSelectString(jdbcOperations)
}

class UserRepositorySpringJdbcMysqlSelectString(client: JdbcOperations) :
    SelectStringRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        client.sqlClient(mysqlTables), MysqlRoles,
        MysqlUsers, MysqlUserRoles
    )
