/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcUpdateDeleteH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2UpdateDelete>() {
    override val context = startContext<UserRepositorySpringJdbcH2UpdateDelete>()

    override val repository = getContextRepository<UserRepositorySpringJdbcH2UpdateDelete>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    private val operator = TransactionTemplate(transactionManager).transactionalOp()

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUsers())
                    .isEqualTo(2)
            assertThat(repository.selectAllUsers())
                    .isEmpty()
        }
    }

    @Test
    fun `Verify deleteUserById works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserById(h2Jdoe.id))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(h2Bboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(h2User.label))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(h2Bboss)
        }
    }

    @Test
    fun `Verify deleteUserIn works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserIn(listOf(h2Jdoe.id, UUID.randomUUID())))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
        }
    }

    @Test
    fun `Verify deleteUserIn no match`() {
        assertThat(repository.deleteUserIn(listOf(UUID.randomUUID(), UUID.randomUUID())))
                .isEqualTo(0)
        assertThat(repository.selectAllUsers())
                .hasSize(2)
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastname("Do"))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", h2User.label))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateAlias works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateAlias("TheBigBoss"))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(h2Bboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo("TheBigBoss")
            assertThat(repository.updateAlias(null))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(h2Bboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo(null)
        }
    }

    @Test
    fun `Verify updateLastnameIn works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastnameIn("Do", listOf(h2Jdoe.id, UUID.randomUUID())))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateLastnameIn no match`() {
        assertThat(repository.updateLastnameIn("Do", listOf(UUID.randomUUID(), UUID.randomUUID())))
                .isEqualTo(0)
        assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Doe")
    }
}


class UserRepositorySpringJdbcH2UpdateDelete(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<H2User>()
            .where { it[H2User::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<H2User>()
            .innerJoin<H2Role>().on { it[H2User::roleId] }
            .where { it[H2Role::label] eq roleLabel }
            .execute()

    fun deleteUserIn(ids: Collection<UUID>) =
            sqlClient.deleteFromTable<H2User>()
                    .where { it[H2User::id] `in` ids }
                    .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<H2User>()
            .set { it[H2User::lastname] = newLastname }
            .where { it[H2User::id] eq h2Jdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<H2User>()
            .set { it[H2User::alias] = newAlias }
            .where { it[H2User::id] eq h2Bboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<H2User>()
            .set { it[H2User::lastname] = newLastname }
            .innerJoin<H2Role>().on { it[H2User::roleId] }
            .where { it[H2Role::label] eq roleLabel }
            .execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<UUID>) =
            sqlClient.updateTable<H2User>()
                    .set { it[H2User::lastname] = newLastname }
                    .where { it[H2User::id] `in` ids }
                    .execute()
}
