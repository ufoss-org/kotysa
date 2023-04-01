/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class JdbcSelectBooleanH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectBoolean>(),
    SelectBooleanTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2SelectBoolean, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SelectBoolean(sqlClient)
}

class UserRepositoryJdbcH2SelectBoolean(sqlClient: JdbcSqlClient) :
    SelectBooleanRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)
