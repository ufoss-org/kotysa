/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorUpdateDeleteTest

class R2dbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryR2dbcH2UpdateDelete>(),
    ReactorUpdateDeleteTest<H2Roles, H2Users, H2UserRoles, UserRepositoryR2dbcH2UpdateDelete,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UserRepositoryR2dbcH2UpdateDelete(sqlClient)
}

class UserRepositoryR2dbcH2UpdateDelete(sqlClient: ReactorSqlClient) :
    ReactorUpdateDeleteRepository<H2Roles, H2Users, H2UserRoles>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles
    )
