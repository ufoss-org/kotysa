/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcUpdateDeleteMssqlTest : AbstractR2dbcMssqlTest<UserRepositoryJdbcMssqlUpdateDelete>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcMssqlUpdateDelete(sqlClient)

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() = runTest {
        coOperator.transactional { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUserRoles())
                .isEqualTo(1)
            assertThat(repository.countAllUserRoles())
                .isEqualTo(0)
        }
        assertThat(repository.countAllUserRoles())
            .isEqualTo(1)
    }

    @Test
    fun `Verify deleteUserById works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserById(userJdoe.id))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers().toList())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserIn works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserIn(listOf(userJdoe.id, 9999999)))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers().toList())
                .hasSize(1)
        }
    }

    @Test
    fun `Verify deleteUserIn no match`() = runTest {
        assertThat(repository.deleteUserIn(listOf(99999, 9999999)))
            .isEqualTo(0)
        assertThat(repository.selectAllUsers().toList())
            .hasSize(2)
    }

    @Test
    fun `Verify deleteUserWithJoin works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(roleUser.label))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers().toList())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify updateLastname works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateLastnameIn works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastnameIn("Do", listOf(userJdoe.id, 9999999)))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateLastnameIn no match`() = runTest {
        assertThat(repository.updateLastnameIn("Do", listOf(99999, 9999999)))
            .isEqualTo(0)
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
            .extracting { user -> user?.lastname }
            .isEqualTo("Doe")
    }

    @Test
    fun `Verify updateWithJoin works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", roleUser.label))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateAlias works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateAlias("TheBigBoss"))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userBboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo("TheBigBoss")
            assertThat(repository.updateAlias(null))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userBboss.firstname))
                .extracting { user -> user?.alias }
                .isEqualTo(null)
        }
    }

    @Test
    fun `Verify updateAndIncrementRoleId works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateAndIncrementRoleId())
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.roleId }
                .isEqualTo(roleGod.id)
        }
    }

    @Test
    fun `Verify updateAndDecrementRoleId works`() = runTest {
        coOperator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateAndDecrementRoleId())
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userBboss.firstname))
                .extracting { user -> user?.roleId }
                .isEqualTo(roleUser.id)
        }
    }
}


class UserRepositoryJdbcMssqlUpdateDelete(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcMssql(sqlClient) {

    suspend fun deleteUserById(id: Int) =
        (sqlClient deleteFrom MssqlUsers
                where MssqlUsers.id eq id
                ).execute()

    suspend fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom MssqlUsers
                where MssqlUsers.id `in` ids
                ).execute()

    suspend fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom MssqlUsers
                innerJoin MssqlRoles on MssqlUsers.roleId eq MssqlRoles.id
                where MssqlRoles.label eq roleLabel
                ).execute()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                where MssqlUsers.id eq userJdoe.id
                ).execute()

    suspend fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                where MssqlUsers.id `in` ids
                ).execute()

    suspend fun updateAlias(newAlias: String?) =
        (sqlClient update MssqlUsers
                set MssqlUsers.alias eq newAlias
                where MssqlUsers.id eq userBboss.id
                ).execute()

    suspend fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update MssqlUsers
                set MssqlUsers.lastname eq newLastname
                innerJoin MssqlRoles on MssqlUsers.roleId eq MssqlRoles.id
                where MssqlRoles.label eq roleLabel
                ).execute()

    suspend fun updateAndIncrementRoleId() =
        (sqlClient update MssqlUsers
                set MssqlUsers.roleId eq MssqlUsers.roleId plus 2
                where MssqlUsers.id eq userJdoe.id
                ).execute()

    suspend fun updateAndDecrementRoleId() =
        (sqlClient update MssqlUsers
                set MssqlUsers.roleId eq MssqlUsers.roleId minus 1
                where MssqlUsers.id eq userBboss.id
                ).execute()
}
