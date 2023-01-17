/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectAndTest


class R2dbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2SelectAnd>(),
    CoroutinesSelectAndTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2SelectAnd, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2SelectAnd(sqlClient)
}

class UserRepositoryJdbcH2SelectAnd(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectAndRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)
