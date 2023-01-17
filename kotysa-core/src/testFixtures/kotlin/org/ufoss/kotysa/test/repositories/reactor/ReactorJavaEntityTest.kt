/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.JavaUsers
import org.ufoss.kotysa.test.UserDto
import org.ufoss.kotysa.test.javaBboss
import org.ufoss.kotysa.test.javaJdoe
import org.ufoss.kotysa.transaction.Transaction
import reactor.kotlin.test.test

interface ReactorJavaEntityTest<T : JavaUsers, U : ReactorJavaUserRepository<T>, V : Transaction> :
    ReactorRepositoryTest<U, V> {

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAll().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(javaJdoe, javaBboss)
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname("John").block())
            .isEqualTo(javaJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown").block())
            .isNull()
    }

    @Test
    fun `Verify selectByAlias1 finds TheBoss`() {
        assertThat(repository.selectByAlias1("TheBoss").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias2 finds TheBoss`() {
        assertThat(repository.selectByAlias2("TheBoss").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias3 finds TheBoss`() {
        assertThat(repository.selectByAlias3("TheBoss").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias1 with null alias finds John`() {
        assertThat(repository.selectByAlias1(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllByAlias2 with null alias finds John`() {
        assertThat(repository.selectByAlias2(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectByAlias3 with null alias finds John`() {
        assertThat(repository.selectByAlias3(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
            )
    }

    @Test
    fun `Verify deleteAll works correctly`() {
        operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteAll()
                .doOnNext { n -> assertThat(n).isEqualTo(2) }
                .thenMany(repository.selectAll())
        }.test()
            .verifyComplete()
    }
}
