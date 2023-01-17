/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.Inheriteds
import org.ufoss.kotysa.test.inherited
import org.ufoss.kotysa.transaction.Transaction

interface CoroutinesInheritanceTest<T : Inheriteds, U : CoroutinesInheritanceRepository<T>, V : Transaction> :
    CoroutinesRepositoryTest<U, V> {

    val table: T

    @Test
    fun `Verify selectInheritedById finds inherited`() = runTest {
        assertThat(repository.selectInheritedById("id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() = runTest {
        assertThat(repository.selectById(table, "id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() = runTest {
        assertThat(repository.selectFirstByName(table, "name"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(table, "id"))
                .isEqualTo(1)
            assertThat(repository.selectAll().toList())
                .isEmpty()
        }
    }
}
