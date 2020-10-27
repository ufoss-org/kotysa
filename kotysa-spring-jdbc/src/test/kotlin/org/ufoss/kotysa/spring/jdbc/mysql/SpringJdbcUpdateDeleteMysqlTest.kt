/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.*
import java.util.*


class SpringJdbcUpdateDeleteMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlUpdateDelete>() {
    override val context = startContext<UserRepositorySpringJdbcMysqlUpdateDelete>()

    override val repository = getContextRepository<UserRepositorySpringJdbcMysqlUpdateDelete>()
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
            assertThat(repository.deleteUserById(mysqlJdoe.id))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(mysqlBboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(mysqlUser.label))
                    .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                    .hasSize(1)
                    .containsOnly(mysqlBboss)
        }
    }

    @Test
    fun `Verify updateLastname works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastname("Do"))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(mysqlJdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", mysqlUser.label))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(mysqlJdoe.firstname))
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
            assertThat(repository.selectFirstByFirstame(mysqlBboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo("TheBigBoss")
            assertThat(repository.updateAlias(null))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(mysqlBboss.firstname))
                    .extracting { user -> user?.alias }
                    .isEqualTo(null)
        }
    }
}


class UserRepositorySpringJdbcMysqlUpdateDelete(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun deleteUserById(id: UUID) = sqlClient.deleteFromTable<MysqlUser>()
            .where { it[MysqlUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<MysqlUser>()
            .innerJoin<MysqlRole>().on { it[MysqlUser::roleId] }
            .where { it[MysqlRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::lastname] = newLastname }
            .where { it[MysqlUser::id] eq mysqlJdoe.id }
            .execute()

    fun updateAlias(newAlias: String?) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::alias] = newAlias }
            .where { it[MysqlUser::id] eq mysqlBboss.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::lastname] = newLastname }
            .innerJoin<MysqlRole>().on { it[MysqlUser::roleId] }
            .where { it[MysqlRole::label] eq roleLabel }
            .execute()
}