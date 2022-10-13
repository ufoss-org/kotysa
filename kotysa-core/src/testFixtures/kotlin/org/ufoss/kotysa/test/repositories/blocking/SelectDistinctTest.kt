/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface SelectDistinctTest<T : Roles, U : Users, V : UserRoles, W : SelectDistinctRepository<T, U, V>, X : Transaction>
    : RepositoryTest<W, X> {

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}
