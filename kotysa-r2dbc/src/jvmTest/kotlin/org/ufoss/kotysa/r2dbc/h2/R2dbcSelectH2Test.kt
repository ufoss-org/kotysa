/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.R2dbcBadGrammarException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectTest


class R2dbcSelectH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2Select>(),
    CoroutinesSelectTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryJdbcH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2Select(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }
}

class UserRepositoryJdbcH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
