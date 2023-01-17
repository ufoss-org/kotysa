/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface ReactorSelectAndTest<T : Roles, U : Users, V : UserRoles, W : ReactorSelectAndRepository<T, U, V>,
        X : Transaction> : ReactorRepositoryTest<W, X> {

    @Test
    fun `Verify selectRolesByLabels finds roleUser`() {
        assertThat(repository.selectRolesByLabels("u", "r").toIterable())
            .hasSize(1)
            .containsExactly(roleUser)
    }
}
