/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*


class R2dbcCoroutinesMariadbTest : AbstractR2dbcMariadbTest<CoroutinesUserMariadbRepository>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        CoroutinesUserMariadbRepository(coSqlClient)

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


class CoroutinesUserMariadbRepository(private val sqlClient: MariadbCoroutinesSqlClient) : Repository {

    override fun init() = runTest {
        createTables()
        insertCompanies()
        insertRoles()
        insertUsers()
    }

    override fun delete() = runTest {
        deleteAllFromUsers()
        deleteAllFromRoles()
        deleteAllFromCompanies()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists MariadbCompanies
        sqlClient createTableIfNotExists MariadbRoles
        sqlClient createTableIfNotExists MariadbUsers
    }

    private suspend fun insertCompanies() = sqlClient.insert(companyBigPharma, companyBigBrother)

    private suspend fun insertRoles() = sqlClient.insert(roleUser, roleAdmin)

    private suspend fun insertUsers() = sqlClient.insert(userJdoe, userBboss)

    suspend fun deleteAllFromUsers() = sqlClient deleteAllFrom MariadbUsers

    private suspend fun deleteAllFromRoles() = sqlClient deleteAllFrom MariadbRoles

    private suspend fun deleteAllFromCompanies() = sqlClient deleteAllFrom MariadbCompanies

    fun selectAllUsers() = sqlClient selectAllFrom MariadbUsers

    suspend fun selectFirstByFirstname(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname eq firstname
                ).fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.firstname eq firstname
                ).fetchFirst()

    suspend fun selectOneNonUnique() =
        (sqlClient selectFrom MariadbUsers
                ).fetchOne()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom MariadbUsers
                where MariadbUsers.alias eq alias
                ).fetchAll()

    fun selectAllMappedToDto() =
        (sqlClient.selectAndBuild {
            UserDto(
                "${it[MariadbUsers.firstname]} ${it[MariadbUsers.lastname]}", it[MariadbUsers.isAdmin]!!,
                it[MariadbUsers.alias]
            )
        }
                from MariadbUsers
                ).fetchAll()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update MariadbUsers
                set MariadbUsers.lastname eq newLastname
                where MariadbUsers.id eq userJdoe.id
                ).execute()

    fun selectAllLimitOffset() =
        (sqlClient selectFrom MariadbUsers
                limit 1 offset 1
                ).fetchAll()
}
