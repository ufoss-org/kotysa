/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class JdbcSelectStringH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectString>(),
    SelectStringTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryJdbcH2SelectString, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SelectString(sqlClient)
}

class UserRepositoryJdbcH2SelectString(sqlClient: JdbcSqlClient) :
    SelectStringRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
