/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDistinctTest

class R2dbcSelectDistinctH2Test : AbstractR2dbcH2Test<UserRepositoryR2dbcH2SelectDistinct>(),
    ReactorSelectDistinctTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryR2dbcH2SelectDistinct,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UserRepositoryR2dbcH2SelectDistinct(sqlClient)
}

class UserRepositoryR2dbcH2SelectDistinct(sqlClient: ReactorSqlClient) :
    ReactorSelectDistinctRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
