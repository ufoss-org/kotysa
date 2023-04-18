/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesUpdateDeleteTest

class R2dbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2UpdateDelete>(),
    CoroutinesUpdateDeleteTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2UpdateDelete, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2UpdateDelete(sqlClient)
}

class UserRepositoryJdbcH2UpdateDelete(sqlClient: R2dbcSqlClient) :
    CoroutinesUpdateDeleteRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)