/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.PostgresqlReactorSqlClient
import org.ufoss.kotysa.test.*


class R2dbcCoroutinesPostgresqlTest : AbstractR2dbcPostgresqlTest<CoroutinesUserPostgresqlRepository>() {

    override fun instantiateRepository(
        sqlClient: PostgresqlReactorSqlClient,
        coSqlClient: PostgresqlCoroutinesSqlClient,
    ) = CoroutinesUserPostgresqlRepository(coSqlClient)

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


class CoroutinesUserPostgresqlRepository(private val sqlClient: PostgresqlCoroutinesSqlClient) : Repository {

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
        sqlClient createTableIfNotExists PostgresqlRoles
        sqlClient createTableIfNotExists PostgresqlUsers
    }

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    private suspend fun deleteAllFromRole() = sqlClient deleteAllFrom PostgresqlRoles

    suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom PostgresqlUsers

    fun selectAllUsers() = sqlClient selectAllFrom PostgresqlUsers

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname eq firstname
                ).fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.firstname eq firstname
                ).fetchFirst()

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom PostgresqlUsers
                ).fetchOne()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom PostgresqlUsers
                where PostgresqlUsers.alias eq alias
                ).fetchAll()

    fun selectAllMappedToDto() =
        (sqlClient.selectAndBuild {
            UserDto(
                "${it[PostgresqlUsers.firstname]} ${it[PostgresqlUsers.lastname]}",
                it[PostgresqlUsers.isAdmin]!!, it[PostgresqlUsers.alias]
            )
        }
                from PostgresqlUsers
                ).fetchAll()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                where PostgresqlUsers.id eq userJdoe.id
                ).execute()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom PostgresqlUsers
                limit 1 offset 1
                ).fetchAll()
}
