/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcUpdateDeleteMariadbTest : AbstractJdbcMariadbTest<UserRepositoryJdbcMariadbUpdateDelete>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcMariadbUpdateDelete(sqlClient)

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
}


class UserRepositoryJdbcMariadbUpdateDelete(private val sqlClient: JdbcSqlClient) :
    AbstractUserRepositoryJdbcMariadb(sqlClient) {

    fun deleteUserById(id: Int) =
            (sqlClient deleteFrom MARIADB_USER
                    where MARIADB_USER.id eq id
                    ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
            (sqlClient deleteFrom MARIADB_USER
                    where MARIADB_USER.id `in` ids
                    ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
            (sqlClient deleteFrom MARIADB_USER
                    innerJoin MARIADB_ROLE on MARIADB_USER.roleId eq MARIADB_ROLE.id
                    where MARIADB_ROLE.label eq roleLabel
                    ).execute()

    fun updateLastname(newLastname: String) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    where MARIADB_USER.id eq userJdoe.id
                    ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    where MARIADB_USER.id `in` ids
                    ).execute()

    fun updateAlias(newAlias: String?) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.alias eq newAlias
                    where MARIADB_USER.id eq userBboss.id
                    ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
            (sqlClient update MARIADB_USER
                    set MARIADB_USER.lastname eq newLastname
                    innerJoin MARIADB_ROLE on MARIADB_USER.roleId eq MARIADB_ROLE.id
                    where MARIADB_ROLE.label eq roleLabel
                    ).execute()
}
