/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class JdbcSelectStringMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectString>(),
    SelectStringTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSelectString, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectString(sqlClient)
}

class UserRepositoryJdbcMysqlSelectString(sqlClient: JdbcSqlClient) :
    SelectStringRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(sqlClient, MysqlRoles, MysqlUsers, MysqlUserRoles)
