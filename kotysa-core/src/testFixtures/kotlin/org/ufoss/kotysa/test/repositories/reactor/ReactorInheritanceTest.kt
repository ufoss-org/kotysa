/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.transaction.Transaction
import reactor.kotlin.test.test

interface ReactorInheritanceTest<T : Inheriteds, U : ReactorInheritanceRepository<T>, V : Transaction> :
    ReactorRepositoryTest<U, V> {

    val table: T

    @Test
    fun `Verify selectInheritedById finds inherited`() {
        assertThat(repository.selectInheritedById("id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify extension function selectById finds inherited`() {
        assertThat(repository.selectById(table, "id").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify selectFirstByName finds inherited`() {
        assertThat(repository.selectFirstByName(table, "name").block())
            .isEqualTo(inherited)
    }

    @Test
    fun `Verify deleteById deletes inherited`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteById(table, "id")
                .doOnNext { n -> assertThat(n).isEqualTo(1) }
                .thenMany(repository.selectAll())
        }.test()
            .verifyComplete()
    }
}
