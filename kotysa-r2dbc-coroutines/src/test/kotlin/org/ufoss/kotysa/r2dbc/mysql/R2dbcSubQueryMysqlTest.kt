/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSubQueryTest

class R2dbcSubQueryMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryR2dbcMysqlSubQuery>(),
    CoroutinesSubQueryTest<MysqlRoles, MysqlUsers, MysqlUserRoles, UserRepositoryR2dbcMysqlSubQuery,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryR2dbcMysqlSubQuery(sqlClient)

    @Tag("miku")
    @Test
    fun `Verify selectCaseWhenExistsSubQuery throws IllegalArgumentException`() {
        Assertions.assertThatThrownBy {
            runTest {
                repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList()
            }
        }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Tag("jasync")
    @Test
    override fun `Verify selectCaseWhenExistsSubQuery returns results`() = runTest {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
                Pair(roleGod.label, false),
            )
    }
}

class UserRepositoryR2dbcMysqlSubQuery(sqlClient: R2dbcSqlClient) :
    CoroutinesSubQueryRepository<MysqlRoles, MysqlUsers, MysqlUserRoles>(
        sqlClient,
        MysqlRoles,
        MysqlUsers,
        MysqlUserRoles
    )
