/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.*
import org.ufoss.kotysa.test.*


class R2DbcCoroutinesH2Test : AbstractR2dbcH2Test<CoroutinesUserH2Repository>() {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        CoroutinesUserH2Repository(coSqlClient)

    @Test
    fun `Verify selectAll returns all users`() = runTest {
        assertThat(repository.selectAllUsers().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() = runTest {
        assertThat(repository.selectFirstByFirstname("John"))
            .isEqualTo(userJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() = runTest {
        assertThat(repository.selectFirstByFirstname("Unknown"))
            .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        assertThatThrownBy {
            runTest { repository.selectFirstByFirstnameNotNullable("Unknown") }
        }.isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy {
            runTest { repository.selectOneNonUnique() }
        }.isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds TheBoss`() = runTest {
        assertThat(repository.selectByAlias("TheBoss").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runTest {
        assertThat(repository.selectByAlias(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(userJdoe)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() = runTest {
        assertThat(repository.selectAllMappedToDto().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", false, null),
                UserDto("Big Boss", true, "TheBoss")
            )
    }

    @Test
    fun `Verify deleteAllFromUser works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUsers())
                .isEqualTo(2)
            assertThat(repository.selectAllUsers().toList())
                .isEmpty()
        }
    }

    @Test
    fun `Verify updateLastname works`() = runTest {
        assertThat(repository.updateLastname("Do"))
            .isEqualTo(1)
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
            .extracting { user -> user?.lastname }
            .isEqualTo("Do")
        repository.updateLastname(userJdoe.lastname)
    }

    @Test
    fun `Verify selectAllLimitOffset returns 1 user`() = runTest {
        assertThat(repository.selectAllLimitOffset().toList())
            .hasSize(1)
    }
}


class CoroutinesUserH2Repository(private val sqlClient: CoroutinesSqlClient) : Repository {

    override fun init() = runTest {
        createTables()
        insertRoles()
        insertUsers()
    }

    override fun delete() = runTest {
        deleteAllFromUsers()
        deleteAllFromRole()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists H2Roles
        sqlClient createTableIfNotExists H2Users
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom H2Roles

    suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom H2Users

    fun selectAllUsers() = sqlClient selectAllFrom H2Users

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom H2Users
                where H2Users.firstname eq firstname
                ).fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom H2Users
                where H2Users.firstname eq firstname
                ).fetchFirst()

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom H2Users
                ).fetchOne()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom H2Users
                where H2Users.alias eq alias
                ).fetchAll()

    fun selectAllMappedToDto() =
        (sqlClient.selectAndBuild {
            UserDto(
                "${it[H2Users.firstname]} ${it[H2Users.lastname]}", it[H2Users.isAdmin]!!,
                it[H2Users.alias]
            )
        }
                from H2Users
                ).fetchAll()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                where H2Users.id eq userJdoe.id
                ).execute()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom H2Users
                limit 1 offset 1
                ).fetchAll()
}
