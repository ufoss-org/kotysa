/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.roleUser


class R2DbcSelectAndH2Test : AbstractR2dbcH2Test<UserRepositoryH2SelectAnd>() {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        UserRepositoryH2SelectAnd(sqlClient)

    @Test
    fun `Verify selectRolesByLabels finds h2User`() {
        assertThat(repository.selectRolesByLabels("u", "r").toIterable())
            .hasSize(1)
            .containsExactly(roleUser)
    }
}


class UserRepositoryH2SelectAnd(sqlClient: ReactorSqlClient) : AbstractUserRepositoryH2(sqlClient) {

    fun selectRolesByLabels(label1: String, label2: String) =
        (sqlClient selectFrom H2Roles
                where H2Roles.label contains label1
                and H2Roles.label contains label2
                ).fetchAll()
}
