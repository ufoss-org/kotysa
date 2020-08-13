/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.r2dbc.coSqlClient
import org.ufoss.kotysa.test.*


@ExperimentalCoroutinesApi
class R2DbcCoroutinesH2Test : AbstractR2dbcPostgresqlTest<CoroutinesUserPostgresqlRepository>() {
    override val context = startContext<CoroutinesUserPostgresqlRepository>()

    override val repository = getContextRepository<CoroutinesUserPostgresqlRepository>()

    @Test
    fun `Verify selectAll returns all users`() = runBlocking<Unit> {
        assertThat(repository.selectAllUsers().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(postgresqlJdoe, postgresqlBboss)
    }

    @Test
    fun `Verify selectFirstByFirstame finds John`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstame("John"))
                .isEqualTo(postgresqlJdoe)
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
                .containsExactlyInAnyOrder(postgresqlBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runBlocking<Unit> {
        assertThat(repository.selectByAlias(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(postgresqlJdoe)
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
        assertThat(repository.deleteAllFromUsers())
                .isEqualTo(2)
        assertThat(repository.selectAllUsers().toList())
                .isEmpty()
        // re-insert users
        repository.insertUsers()
    }

    @Test
    fun `Verify updateLastname works`() = runBlocking<Unit> {
        assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(postgresqlJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(postgresqlJdoe.lastname)
    }
}


class CoroutinesUserPostgresqlRepository(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.coSqlClient(postgresqlTables)

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
        sqlClient.createTable<PostgresqlRole>()
        sqlClient.createTable<PostgresqlUser>()
    }

    private suspend fun insertRoles() = sqlClient.insert(postgresqlUser, postgresqlAdmin)

    suspend fun insertUsers() = sqlClient.insert(postgresqlJdoe, postgresqlBboss)

    private suspend fun deleteAllFromRole() = sqlClient.deleteAllFromTable<PostgresqlRole>()

    suspend fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<PostgresqlUser>()

    fun selectAllUsers() = sqlClient.selectAll<PostgresqlUser>()

    suspend fun selectFirstByFirstame(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirstOrNull()

    suspend fun selectFirstByFirstameNotNullable(firstname: String) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::firstname] eq firstname }
            .fetchFirst()

    suspend fun selectOneNonUnique() = sqlClient.select<PostgresqlUser>()
            .fetchOne()

    fun selectByAlias(alias: String?) = sqlClient.select<PostgresqlUser>()
            .where { it[PostgresqlUser::alias] eq alias }
            .fetchAll()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[PostgresqlUser::firstname]} ${it[PostgresqlUser::lastname]}",
                        it[PostgresqlUser::alias])
            }.fetchAll()

    suspend fun updateLastname(newLastname: String) = sqlClient.updateTable<PostgresqlUser>()
            .set { it[PostgresqlUser::lastname] = newLastname }
            .where { it[PostgresqlUser::id] eq postgresqlJdoe.id }
            .execute()
}
