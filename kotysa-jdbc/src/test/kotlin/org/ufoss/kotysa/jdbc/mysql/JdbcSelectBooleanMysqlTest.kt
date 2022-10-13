/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class JdbcSelectBooleanMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSelectBoolean>(),
    SelectBooleanTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSelectBoolean, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSelectBoolean(sqlClient)
}

class UserRepositoryJdbcMysqlSelectBoolean(sqlClient: JdbcSqlClient) :
    SelectBooleanRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(sqlClient, MysqlRoles, MysqlUsers, MysqlUserRoles)
