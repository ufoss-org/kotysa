/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.r2dbc.core.DatabaseClient


class R2DbcSelectOrH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectOr>() {
    override val context = startContext<UserRepositoryH2SelectOr>()

    override val repository = getContextRepository<UserRepositoryH2SelectOr>()

    @Test
    fun `Verify selectRolesByLabels finds h2Admin and h2God`() {
        assertThat(repository.selectRolesByLabels(h2Admin.label, h2God.label).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Admin, h2God)
    }
}


class UserRepositoryH2SelectOr(dbClient: DatabaseClient) : AbstractUserRepositoryH2(dbClient) {

    fun selectRolesByLabels(label1: String, label2: String) = sqlClient.select<H2Role>()
            .where { it[H2Role::label] eq label1 }
            .or { it[H2Role::label] eq label2 }
            .fetchAll()
}
