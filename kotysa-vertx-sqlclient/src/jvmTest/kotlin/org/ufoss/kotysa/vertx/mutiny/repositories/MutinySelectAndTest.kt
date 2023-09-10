/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

interface MutinySelectAndTest<T : Roles, U : Users, V : UserRoles, W : MutinySelectAndRepository<T, U, V>>
    : MutinyRepositoryTest<W> {

    @Test
    fun `Verify selectRolesByLabels finds roleUser`() {
        assertThat(repository.selectRolesByLabels("u", "r").await().indefinitely())
            .hasSize(1)
            .containsExactly(roleUser)
    }
}
