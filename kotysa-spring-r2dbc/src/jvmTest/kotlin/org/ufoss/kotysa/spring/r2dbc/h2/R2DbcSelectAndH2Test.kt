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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectAndTest

class R2DbcSelectAndH2Test : AbstractR2dbcH2Test<ReactorUserRepositoryH2SelectAnd>(),
    ReactorSelectAndTest<H2Roles, H2Users, H2UserRoles, ReactorUserRepositoryH2SelectAnd,
            ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorUserRepositoryH2SelectAnd(sqlClient)
}

class ReactorUserRepositoryH2SelectAnd(sqlClient: ReactorSqlClient) :
    ReactorSelectAndRepository<H2Roles, H2Users, H2UserRoles>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles
    )
