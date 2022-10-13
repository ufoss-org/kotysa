/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.blocking

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction

interface InheritanceTest<T : Inheriteds, U : InheritanceRepository<T>, V : Transaction> : RepositoryTest<U, V> {

    val table: T

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(table, "id"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(table, "name"))
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteById(table, "id"))
                .isEqualTo(1)
            assertThat(repository.selectAll())
                .isEmpty()
        }
    }
}
