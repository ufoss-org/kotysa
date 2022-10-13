/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class JdbcSelectAndMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectAnd>(),
    SelectAndTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSelectAnd, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectAnd(sqlClient)
}

class UserRepositoryJdbcMysqlSelectAnd(sqlClient: JdbcSqlClient) :
    SelectAndRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(sqlClient, MysqlRoles, MysqlUsers, MysqlUserRoles)
