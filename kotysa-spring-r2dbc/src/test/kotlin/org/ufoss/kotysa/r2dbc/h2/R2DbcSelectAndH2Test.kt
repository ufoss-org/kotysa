/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.H2Role
import org.ufoss.kotysa.test.h2God


class R2DbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectAnd>() {
    override val context = startContext<UserRepositoryH2SelectAnd>()

    override val repository = getContextRepository<UserRepositoryH2SelectAnd>()

    @Test
    fun `Verify selectRolesByLabels finds h2God`() {
        assertThat(repository.selectRolesByLabels("d", "g").toIterable())
                .hasSize(1)
                .containsExactly(h2God)
    }
}


class UserRepositoryH2SelectAnd(
        sqlClient: ReactorSqlClient,
        transactionalOperator: TransactionalOperator
) : AbstractUserRepositoryH2(sqlClient, transactionalOperator) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<H2Role>()
            .where { it[H2Role::label] contains label1 }
            .and { it[H2Role::label] contains label2 }
            .fetchAll()
}
