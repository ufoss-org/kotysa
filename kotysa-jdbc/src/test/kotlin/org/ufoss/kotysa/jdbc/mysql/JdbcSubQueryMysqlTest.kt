/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlRoles
import org.ufoss.kotysa.test.MysqlUserRoles
import org.ufoss.kotysa.test.MysqlUsers
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class JdbcSubQueryMysqlTest : AbstractJdbcMysqlTest<UserRepositoryJdbcMysqlSubQuery>(),
    SubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryJdbcMysqlSubQuery, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMysqlSubQuery(sqlClient)
}

class UserRepositoryJdbcMysqlSubQuery(sqlClient: JdbcSqlClient) :
    SubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(sqlClient, MysqlRoles, MysqlUsers, MysqlUserRoles)
