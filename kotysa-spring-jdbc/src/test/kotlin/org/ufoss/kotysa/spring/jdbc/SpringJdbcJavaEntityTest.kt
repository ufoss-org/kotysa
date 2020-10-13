/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.UserDto
import org.ufoss.kotysa.test.javaBboss
import org.ufoss.kotysa.test.javaJdoe

interface SpringJdbcJavaEntityTest<T : JavaUserRepository> {

    val repository: T
    val context: ConfigurableApplicationContext

    private val transactionManager get() = context.getBean<PlatformTransactionManager>()
    private val operator get() = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify selectAll returns all users`() {
        Assertions.assertThat(repository.selectAll())
                .hasSize(2)
                .containsExactlyInAnyOrder(javaJdoe, javaBboss)
    }

    @Test
    fun `Verify selectFirstByFirstame finds John`() {
        Assertions.assertThat(repository.selectFirstByFirstame("John"))
                .isEqualTo(javaJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstame finds no Unknown`() {
        Assertions.assertThat(repository.selectFirstByFirstame("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectByAlias1 finds TheBoss`() {
        Assertions.assertThat(repository.selectByAlias1("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias2 finds TheBoss`() {
        Assertions.assertThat(repository.selectByAlias2("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias3 finds TheBoss`() {
        Assertions.assertThat(repository.selectByAlias3("TheBoss"))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaBboss)
    }

    @Test
    fun `Verify selectByAlias1 with null alias finds John`() {
        Assertions.assertThat(repository.selectByAlias1(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllByAlias2 with null alias finds John`() {
        Assertions.assertThat(repository.selectByAlias2(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectByAlias3 with null alias finds John`() {
        Assertions.assertThat(repository.selectByAlias3(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(javaJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        Assertions.assertThat(repository.selectAllMappedToDto())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss"))
    }

    @Test
    fun `Verify deleteAll works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            Assertions.assertThat(repository.deleteAll())
                    .isEqualTo(2)
            Assertions.assertThat(repository.selectAll())
                    .isEmpty()
        }
    }
}
