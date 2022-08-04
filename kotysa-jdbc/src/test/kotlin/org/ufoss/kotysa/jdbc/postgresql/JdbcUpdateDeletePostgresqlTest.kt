/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcUpdateDeletePostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlUpdateDelete>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient)

    @Test
    fun `Verify deleteAllFromUserRoles works correctly`() {
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
    fun `Verify deleteUserById works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserById(userJdoe.id))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(roleUser.label))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                .hasSize(1)
                .containsOnly(userBboss)
        }
    }

    @Test
    fun `Verify deleteUserIn works`() {
        operator.transactional<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserIn(listOf(userJdoe.id, 9999999)))
                .isEqualTo(1)
            assertThat(repository.selectAllUsers())
                .hasSize(1)
        }
    }

    @Test
    fun `Verify deleteUserIn no match`() {
        assertThat(repository.deleteUserIn(listOf(99999, 9999999)))
            .isEqualTo(0)
        assertThat(repository.selectAllUsers())
            .hasSize(2)
    }

    @Test
    fun `Verify updateLastname works`() {
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
    fun `Verify updateLastnameIn works`() {
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
    fun `Verify updateLastnameIn no match`() {
        assertThat(repository.updateLastnameIn("Do", listOf(99999, 9999999)))
            .isEqualTo(0)
        assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
            .extracting { user -> user?.lastname }
            .isEqualTo("Doe")
    }

    @Test
    fun `Verify updateAlias works`() {
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
    fun `Verify updateWithJoin works`() {
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
    fun `Verify updateAndIncrementRoleId works`() {
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
    fun `Verify updateAndDecrementRoleId works`() {
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


class UserRepositoryJdbcPostgresqlUpdateDelete(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcPostgresql(sqlClient) {

    fun deleteUserById(id: Int) =
        (sqlClient deleteFrom PostgresqlUsers
                where PostgresqlUsers.id eq id
                ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom PostgresqlUsers
                where PostgresqlUsers.id `in` ids
                ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom PostgresqlUsers
                innerJoin PostgresqlRoles on PostgresqlUsers.roleId eq PostgresqlRoles.id
                where PostgresqlRoles.label eq roleLabel
                ).execute()

    fun updateLastname(newLastname: String) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                where PostgresqlUsers.id eq userJdoe.id
                ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                where PostgresqlUsers.id `in` ids
                ).execute()

    fun updateAlias(newAlias: String?) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.alias eq newAlias
                where PostgresqlUsers.id eq userBboss.id
                ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.lastname eq newLastname
                innerJoin PostgresqlRoles on PostgresqlUsers.roleId eq PostgresqlRoles.id
                where PostgresqlRoles.label eq roleLabel
                ).execute()

    fun updateAndIncrementRoleId() =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.roleId eq PostgresqlUsers.roleId plus 2
                where PostgresqlUsers.id eq userJdoe.id
                ).execute()

    fun updateAndDecrementRoleId() =
        (sqlClient update PostgresqlUsers
                set PostgresqlUsers.roleId eq PostgresqlUsers.roleId minus 1
                where PostgresqlUsers.id eq userBboss.id
                ).execute()
}
