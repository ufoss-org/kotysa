/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSubQueryTest

class R2dbcSubQueryMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSubQuery>(),
    ReactorSubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryR2dbcMysqlSubQuery,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryR2dbcMysqlSubQuery(sqlClient)

    @Tag("asyncer")
    @Test
    fun `Verify selectCaseWhenExistsSubQuery throws IllegalArgumentException`() {
        Assertions.assertThatThrownBy {
            repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).blockLast()
        }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Tag("jasync")
    @Test
    override fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toIterable())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }
}


class UserRepositoryR2dbcMysqlSubQuery(sqlClient: ReactorSqlClient) :
    ReactorSubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
