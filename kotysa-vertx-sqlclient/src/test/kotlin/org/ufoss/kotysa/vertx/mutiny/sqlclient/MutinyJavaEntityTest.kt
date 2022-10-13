/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.JavaUsers
import org.ufoss.kotysa.test.UserDto
import org.ufoss.kotysa.test.javaBboss
import org.ufoss.kotysa.test.javaJdoe

// TODO move all tests following this model
interface MutinyJavaEntityTest<T : JavaUsers, U : MutinyJavaUserRepository<T>> : MutinyRepositoryTest<U> {

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAll().await().indefinitely())
                .hasSize(2)
                .containsExactlyInAnyOrder(javaJdoe, javaBboss)
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname("John").await().indefinitely())
                .isEqualTo(javaJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown").await().indefinitely())
                .isNull()
    }

    @Test
    fun `Verify selectByAlias1 finds TheBoss`() {
        assertThat(repository.selectByAlias1("TheBoss").await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias2 finds TheBoss`() {
        assertThat(repository.selectByAlias2("TheBoss").await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias3 finds TheBoss`() {
        assertThat(repository.selectByAlias3("TheBoss").await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias1 with null alias finds John`() {
        assertThat(repository.selectByAlias1(null).await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllByAlias2 with null alias finds John`() {
        assertThat(repository.selectByAlias2(null).await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectByAlias3 with null alias finds John`() {
        assertThat(repository.selectByAlias3(null).await().indefinitely())
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().await().indefinitely())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss"))
    }

    @Test
    fun `Verify deleteAll works correctly`() {
        val users = operator.transactional { transaction ->
            transaction.setRollbackOnly()
            repository.deleteAll()
                    .onItem().invoke { n -> assertThat(n).isEqualTo(2) }
                    .chain { -> repository.selectAll() }
        }.await().indefinitely()
        assertThat(users).isEmpty()
    }
}
