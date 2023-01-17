/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesSelectDistinctTest<T : Roles, U : Users, V : UserRoles,
        W : CoroutinesSelectDistinctRepository<T, U, V>, X : Transaction> : CoroutinesRepositoryTest<W, X> {

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() = runTest {
        assertThat(repository.selectDistinctRoleLabels().toList())
            .hasSize(3)
            .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}
