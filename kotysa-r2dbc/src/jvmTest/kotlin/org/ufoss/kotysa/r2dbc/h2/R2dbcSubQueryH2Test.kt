/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryH2Test : AbstractR2dbcH2Test<UserRepositoryR2dbcH2SubQuery>(),
    CoroutinesSubQueryTest<H2Roles, H2Users, H2UserRoles, UserRepositoryR2dbcH2SubQuery, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcH2SubQuery(sqlClient)
}

class UserRepositoryR2dbcH2SubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)
