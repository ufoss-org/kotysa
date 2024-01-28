/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.BadSqlGrammarException
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTest

class R2DbcSelectH2Test : AbstractR2dbcH2Test<UserRepositoryH2Select>(),
    ReactorSelectTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryH2Select, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UserRepositoryH2Select(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositoryH2Select(sqlClient: ReactorSqlClient) :
    ReactorSelectRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
