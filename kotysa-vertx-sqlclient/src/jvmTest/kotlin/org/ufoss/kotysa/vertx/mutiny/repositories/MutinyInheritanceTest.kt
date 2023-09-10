/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.repositories

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*

interface MutinyInheritanceTest<T : Inheriteds, U : MutinyInheritanceRepository<T>> : MutinyRepositoryTest<U> {

    val table: T

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(table, "id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(table, "name").await().indefinitely())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        val all = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(table, "id")
                .onItem().invoke { n -> assertThat(n).isEqualTo(1) }
                .chain { -> repository.selectAll() }
        }.await().indefinitely()
        assertThat(all).isEmpty()
    }
}
