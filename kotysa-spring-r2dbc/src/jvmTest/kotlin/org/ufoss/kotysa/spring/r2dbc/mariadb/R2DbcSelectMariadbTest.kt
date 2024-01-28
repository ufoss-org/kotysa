/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.BadSqlGrammarException
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MariadbCompanies
import org.ufoss.kotysa.test.MariadbRoles
import org.ufoss.kotysa.test.MariadbUserRoles
import org.ufoss.kotysa.test.MariadbUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectTest

class R2DbcSelectMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelect>(),
    ReactorSelectTest<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies, UserRepositoryMariadbSelect,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        UserRepositoryMariadbSelect(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositoryMariadbSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectRepository<MariadbRoles, MariadbUsers, MariadbUserRoles, MariadbCompanies>(
        sqlClient,
        MariadbRoles,
        MariadbUsers,
        MariadbUserRoles,
        MariadbCompanies
    )
