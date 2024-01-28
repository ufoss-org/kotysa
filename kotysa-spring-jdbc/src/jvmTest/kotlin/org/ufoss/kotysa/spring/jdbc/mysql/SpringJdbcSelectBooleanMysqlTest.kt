/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelectBoolean>(),
    SelectBooleanTest<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies,
            UserRepositorySpringJdbcMysqlSelectBoolean, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcMysqlSelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcMysqlSelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<MysqlRoles, MysqlUsers, MysqlUserRoles, MysqlCompanies>(
        client.sqlClient(mysqlTables),
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles,
        MysqlCompanies
    )
