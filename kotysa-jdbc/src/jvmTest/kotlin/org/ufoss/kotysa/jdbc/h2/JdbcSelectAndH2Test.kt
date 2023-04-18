/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class JdbcSelectAndH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectAnd>(),
    SelectAndTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2SelectAnd, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SelectAnd(sqlClient)
}

class UserRepositoryJdbcH2SelectAnd(sqlClient: JdbcSqlClient) :
    SelectAndRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)