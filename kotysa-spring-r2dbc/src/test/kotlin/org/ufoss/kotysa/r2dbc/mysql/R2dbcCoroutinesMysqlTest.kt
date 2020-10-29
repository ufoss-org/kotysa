/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.test.*


@ExperimentalCoroutinesApi
class R2dbcCoroutinesMysqlTest : AbstractR2dbcMysqlTest<CoroutinesUserMysqlRepository>() {
    override val context = startContext<CoroutinesUserMysqlRepository>()

    override val repository = getContextRepository<CoroutinesUserMysqlRepository>()
    private val operator = context.getBean<TransactionalOperator>().coTransactionalOp()

    @Test
    fun `Verify selectAll returns all users`() = runBlocking<Unit> {
        assertThat(repository.selectAllUsers().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(mysqlJdoe, mysqlBboss)
    }

    @Test
    fun `Verify selectFirstByFirstame finds John`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstame("John"))
                .isEqualTo(mysqlJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstame finds no Unknown`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstame("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstameNotNullable finds no Unknown, throws NoResultException`() {
        assertThatThrownBy {
            runBlocking<Unit> { repository.selectFirstByFirstameNotNullable("Unknown") }
        }.isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy {
            runBlocking<Unit> { repository.selectOneNonUnique() }
        }.isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds TheBoss`() = runBlocking<Unit> {
        assertThat(repository.selectByAlias("TheBoss").toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runBlocking<Unit> {
        assertThat(repository.selectByAlias(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() = runBlocking<Unit> {
        assertThat(repository.selectAllMappedToDto().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss"))
    }

    @Test
    fun `Verify deleteAllFromUser works correctly`() = runBlocking<Unit> {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUsers())
                    .isEqualTo(2)
            assertThat(repository.selectAllUsers().toList())
                    .isEmpty()
        }
    }

    @Test
    fun `Verify updateLastname works`() = runBlocking<Unit> {
        assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(mysqlJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(mysqlJdoe.lastname)
    }
}


class CoroutinesUserMysqlRepository(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.coSqlClient(mysqlTables)

    override fun init() = runBlocking {
        createTables()
        insertRoles()
        insertUsers()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private suspend fun createTables() {
        sqlClient.createTable<MysqlRole>()
        sqlClient.createTable<MysqlUser>()
    }

    private suspend fun insertRoles() = sqlClient.insert(mysqlUser, mysqlAdmin)

    suspend fun insertUsers() = sqlClient.insert(mysqlJdoe, mysqlBboss)

    private suspend fun deleteAllFromRole() = sqlClient.deleteAllFromTable<MysqlRole>()

    suspend fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<MysqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<MysqlUser>()

    suspend fun selectFirstByFirstame(firstname: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] eq firstname }
            .fetchFirstOrNull()

    suspend fun selectFirstByFirstameNotNullable(firstname: String) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::firstname] eq firstname }
            .fetchFirst()

    suspend fun selectOneNonUnique() = sqlClient.select<MysqlUser>()
            .fetchOne()

    fun selectByAlias(alias: String?) = sqlClient.select<MysqlUser>()
            .where { it[MysqlUser::alias] eq alias }
            .fetchAll()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[MysqlUser::firstname]} ${it[MysqlUser::lastname]}",
                        it[MysqlUser::alias])
            }.fetchAll()

    suspend fun updateLastname(newLastname: String) = sqlClient.updateTable<MysqlUser>()
            .set { it[MysqlUser::lastname] = newLastname }
            .where { it[MysqlUser::id] eq mysqlJdoe.id }
            .execute()
}
