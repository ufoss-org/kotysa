/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*


class R2DbcCoroutinesH2Test : AbstractR2dbcH2Test<CoroutinesUserH2Repository>() {

    @BeforeAll
    fun beforeAll() {
        context = startContext<CoroutinesUserH2Repository>()
        repository = getContextRepository()
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
    fun `Verify selectFirstByFirstname finds no Unknown`() = runBlocking {
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


class CoroutinesUserH2Repository(private val sqlClient: CoroutinesSqlClient) : Repository {

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
        sqlClient createTable H2_ROLE
        sqlClient createTable H2_USER
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom H2_ROLE

    suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom H2_USER

    fun selectAllUsers() = sqlClient selectAllFrom H2_USER

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.firstname eq firstname
                    ).fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.firstname eq firstname
                    ).fetchFirst()

    suspend fun selectOneNonUnique() =
            (sqlClient selectFrom H2_USER
                    ).fetchOne()

    fun selectByAlias(alias: String?) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.alias eq alias
                    ).fetchAll()

    fun selectAllMappedToDto() =
            (sqlClient.select {
                UserDto("${it[H2_USER.firstname]} ${it[H2_USER.lastname]}",
                        it[H2_USER.alias])
            }
                    from H2_USER
                    ).fetchAll()

    suspend fun updateLastname(newLastname: String) =
            (sqlClient update H2_USER
                    set H2_USER.lastname eq newLastname
                    where H2_USER.id eq userJdoe.id
                    ).execute()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom H2_USER
                    limit 1 offset 1
                    ).fetchAll()
}
