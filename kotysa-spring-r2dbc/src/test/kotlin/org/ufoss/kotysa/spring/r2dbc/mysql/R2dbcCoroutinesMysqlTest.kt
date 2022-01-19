/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.spring.r2dbc.coSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2dbcCoroutinesMysqlTest : AbstractR2dbcMysqlTest<CoroutinesUserMysqlRepository>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<CoroutinesUserMysqlRepository>(resource)
    }

    override val repository: CoroutinesUserMysqlRepository by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAll returns all users`() = runBlocking<Unit> {
        assertThat(repository.selectAllUsers().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstname("John"))
                .isEqualTo(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstname("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        assertThatThrownBy {
            runBlocking<Unit> { repository.selectFirstByFirstnameNotNullable("Unknown") }
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
                .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runBlocking<Unit> {
        assertThat(repository.selectByAlias(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(userJdoe)
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
        coOperator.execute { transaction ->
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
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(userJdoe.lastname)
    }

    @Test
    fun `Verify selectAllLimitOffset returns 1 user`() = runBlocking<Unit> {
        assertThat(repository.selectAllLimitOffset().toList())
                .hasSize(1)
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
        sqlClient createTableIfNotExists MYSQL_ROLE
        sqlClient createTableIfNotExists MYSQL_USER
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom MYSQL_ROLE

    suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MYSQL_USER

    fun selectAllUsers() = sqlClient selectAllFrom MYSQL_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.firstname eq firstname
                    ).fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.firstname eq firstname
                    ).fetchFirst()

    suspend fun selectOneNonUnique() =
            (sqlClient selectFrom MYSQL_USER
                    ).fetchOne()

    fun selectByAlias(alias: String?) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.alias eq alias
                    ).fetchAll()

    fun selectAllMappedToDto() =
            (sqlClient.select {
                UserDto("${it[MYSQL_USER.firstname]} ${it[MYSQL_USER.lastname]}",
                        it[MYSQL_USER.alias])
            }
                    from MYSQL_USER
                    ).fetchAll()

    suspend fun updateLastname(newLastname: String) =
            (sqlClient update MYSQL_USER
                    set MYSQL_USER.lastname eq newLastname
                    where MYSQL_USER.id eq userJdoe.id
                    ).execute()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MYSQL_USER
                    limit 1 offset 1
                    ).fetchAll()
}
