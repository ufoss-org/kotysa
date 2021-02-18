/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2
/*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.getBean
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.test.*


@ExperimentalCoroutinesApi
class R2DbcCoroutinesH2Test : AbstractR2dbcH2Test<CoroutinesUserH2Repository>() {
    override val context = startContext<CoroutinesUserH2Repository>()

    override val repository = getContextRepository<CoroutinesUserH2Repository>()
    private val operator = context.getBean<TransactionalOperator>().coTransactionalOp()

    @Test
    fun `Verify selectAll returns all users`() = runBlocking<Unit> {
        assertThat(repository.selectAllUsers().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(h2Jdoe, h2Bboss)
    }

    @Test
    fun `Verify selectFirstByFirstname finds John`() = runBlocking<Unit> {
        assertThat(repository.selectFirstByFirstname("John"))
                .isEqualTo(h2Jdoe)
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
                .containsExactlyInAnyOrder(h2Bboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() = runBlocking<Unit> {
        assertThat(repository.selectByAlias(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(h2Jdoe)
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
        assertThat(repository.selectFirstByFirstname(h2Jdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(h2Jdoe.lastname)
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
        sqlClient.createTable<H2Role>()
        sqlClient.createTable<H2User>()
    }

    private suspend fun insertRoles() = sqlClient.insert(h2User, h2Admin)

    suspend fun insertUsers() = sqlClient.insert(h2Jdoe, h2Bboss)

    private suspend fun deleteAllFromRole() = sqlClient.deleteAllFromTable<H2Role>()

    suspend fun deleteAllFromUsers() = sqlClient.deleteAllFromTable<H2User>()

    fun selectAllUsers() = sqlClient.selectAll<H2User>()

    suspend fun selectFirstByFirstname(firstname: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] eq firstname }
            .fetchFirstOrNull()

    suspend fun selectFirstByFirstnameNotNullable(firstname: String) = sqlClient.select<H2User>()
            .where { it[H2User::firstname] eq firstname }
            .fetchFirst()

    suspend fun selectOneNonUnique() = sqlClient.select<H2User>()
            .fetchOne()

    fun selectByAlias(alias: String?) = sqlClient.select<H2User>()
            .where { it[H2User::alias] eq alias }
            .fetchAll()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[H2User::firstname]} ${it[H2User::lastname]}",
                        it[H2User::alias])
            }.fetchAll()

    suspend fun updateLastname(newLastname: String) = sqlClient.updateTable<H2User>()
            .set { it[H2User::lastname] = newLastname }
            .where { it[H2User::id] eq h2Jdoe.id }
            .execute()
}
*/