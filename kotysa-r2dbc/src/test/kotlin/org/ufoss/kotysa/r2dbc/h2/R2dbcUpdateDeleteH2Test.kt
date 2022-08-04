/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcUpdateDeleteH2Test : AbstractR2dbcH2Test<UserRepositoryJdbcH2UpdateDelete>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcH2UpdateDelete(sqlClient)

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() = runTest {
        operator.transactional { transaction ->
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
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserById(userJdoe.id))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers().toList())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() = runTest {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(roleUser.label))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers().toList())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserIn works`() = runTest {
        operator.transactional<Unit> { transaction ->
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
    fun `Verify updateLastname works`() = runTest {
        operator.transactional<Unit> { transaction ->
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
        operator.transactional<Unit> { transaction ->
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
    fun `Verify updateAlias works`() = runTest {
        operator.transactional<Unit> { transaction ->
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
    fun `Verify updateWithJoin works`() = runTest {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", roleUser.label))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateAndIncrementRoleId works`() = runTest {
        operator.transactional<Unit> { transaction ->
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
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateAndDecrementRoleId())
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userBboss.firstname))
                .extracting { user -> user?.roleId }
                .isEqualTo(roleUser.id)
        }
    }
}


class UserRepositoryJdbcH2UpdateDelete(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcH2(sqlClient) {

    suspend fun deleteUserById(id: Int) =
        (sqlClient deleteFrom H2Users
                where H2Users.id eq id
                ).execute()

    suspend fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom H2Users
                where H2Users.id `in` ids
                ).execute()

    suspend fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom H2Users
                innerJoin H2Roles on H2Users.roleId eq H2Roles.id
                where H2Roles.label eq roleLabel
                ).execute()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                where H2Users.id eq userJdoe.id
                ).execute()

    suspend fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                where H2Users.id `in` ids
                ).execute()

    suspend fun updateAlias(newAlias: String?) =
        (sqlClient update H2Users
                set H2Users.alias eq newAlias
                where H2Users.id eq userBboss.id
                ).execute()

    suspend fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update H2Users
                set H2Users.lastname eq newLastname
                innerJoin H2Roles on H2Users.roleId eq H2Roles.id
                where H2Roles.label eq roleLabel
                ).execute()

    suspend fun updateAndIncrementRoleId() =
        (sqlClient update H2Users
                set H2Users.roleId eq H2Users.roleId plus 2
                where H2Users.id eq userJdoe.id
                ).execute()

    suspend fun updateAndDecrementRoleId() =
        (sqlClient update H2Users
                set H2Users.roleId eq H2Users.roleId minus 1
                where H2Users.id eq userBboss.id
                ).execute()
}
