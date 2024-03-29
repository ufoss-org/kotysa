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

interface CoroutinesSelectAndTest<T : Roles, U : Users, V : UserRoles, W : Companies,
        X : CoroutinesSelectAndRepository<T, U, V, W>, Y : Transaction> : CoroutinesRepositoryTest<X, Y> {

    @Test
    fun `verify selectRolesByLabels finds roleUser`() = runTest {
        assertThat(repository.selectRolesByLabels("u", "r").toList())
            .hasSize(1)
            .containsExactly(roleUser)
    }
}
