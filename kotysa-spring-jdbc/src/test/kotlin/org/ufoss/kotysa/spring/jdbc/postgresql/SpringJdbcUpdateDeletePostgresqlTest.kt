/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcUpdateDeletePostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlUpdateDelete>() {
    override val context = startContext<UserRepositorySpringJdbcPostgresqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositorySpringJdbcPostgresqlUpdateDelete>()
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
            assertThat(repository.deleteUserById(postgresqlJdoe.id))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(postgresqlBboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(postgresqlUser.label))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(postgresqlBboss)
        }
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastname("Do"))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", postgresqlUser.label))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
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
            assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo("TheBigBoss")
            assertThat(repository.updateAlias(null))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(postgresqlBboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo(null)
        }
    }
}


class UserRepositorySpringJdbcPostgresqlUpdateDelete(client: JdbcTemplate) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<PostgresqlUser>()
            .where { it[PostgresqlUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<PostgresqlUser>()
            .innerJoin<PostgresqlRole>().on { it[PostgresqlUser::roleId] }
            .where { it[PostgresqlRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::lastname] = newLastname }
            .where { it[PostgresqlUser::id] eq postgresqlJdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::alias] = newAlias }
            .where { it[PostgresqlUser::id] eq postgresqlBboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::lastname] = newLastname }
            .innerJoin<PostgresqlRole>().on { it[PostgresqlUser::roleId] }
            .where { it[PostgresqlRole::label] eq roleLabel }
            .execute()
}
