/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectStringTest


class R2dbcSelectStringH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectString>(),
    CoroutinesSelectStringTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryJdbcH2SelectString,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectString(sqlClient)
}

class UserRepositoryJdbcH2SelectString(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectStringRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
