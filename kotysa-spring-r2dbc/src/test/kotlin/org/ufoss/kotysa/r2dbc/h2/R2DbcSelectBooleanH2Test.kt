/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2User
import org.ufoss.kotysa.test.h2Bboss
import org.ufoss.kotysa.test.h2Jdoe


class R2DbcSelectBooleanH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectBoolean>() {
    override val context = startContext<UserRepositoryH2SelectBoolean>()

    override val repository = getContextRepository<UserRepositoryH2SelectBoolean>()

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true).toIterable())
                .hasSize(1)
                .containsExactly(h2Bboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false).toIterable())
                .hasSize(1)
                .containsExactly(h2Jdoe)
    }
}


class UserRepositoryH2SelectBoolean(
        sqlClient: ReactorSqlClient,
        transactionalOperator: TransactionalOperator
) : AbstractUserRepositoryH2(sqlClient, transactionalOperator) {

    fun selectAllByIsAdminEq(value: Boolean) = sqlClient.select<H2User>()
            .where { it[H2User::isAdmin] eq value }
            .fetchAll()
}
