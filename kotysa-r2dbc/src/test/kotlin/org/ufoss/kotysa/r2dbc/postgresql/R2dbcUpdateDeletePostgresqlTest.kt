/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

class R2dbcUpdateDeletePostgresqlTest : AbstractR2dbcPostgresqlTest<UserRepositoryJdbcPostgresqlUpdateDelete>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositoryJdbcPostgresqlUpdateDelete(sqlClient)

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
}


class UserRepositoryJdbcPostgresqlUpdateDelete(private val sqlClient: R2dbcSqlClient) :
    AbstractUserRepositoryR2dbcPostgresql(sqlClient) {

    suspend fun deleteUserById(id: Int) =
        (sqlClient deleteFrom POSTGRESQL_USER
                where POSTGRESQL_USER.id eq id
                ).execute()

    suspend fun deleteUserIn(ids: Collection<Int>) =
        (sqlClient deleteFrom POSTGRESQL_USER
                where POSTGRESQL_USER.id `in` ids
                ).execute()

    suspend fun deleteUserWithJoin(roleLabel: String) =
        (sqlClient deleteFrom POSTGRESQL_USER
                innerJoin POSTGRESQL_ROLE on POSTGRESQL_USER.roleId eq POSTGRESQL_ROLE.id
                where POSTGRESQL_ROLE.label eq roleLabel
                ).execute()

    suspend fun updateLastname(newLastname: String) =
        (sqlClient update POSTGRESQL_USER
                set POSTGRESQL_USER.lastname eq newLastname
                where POSTGRESQL_USER.id eq userJdoe.id
                ).execute()

    suspend fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
        (sqlClient update POSTGRESQL_USER
                set POSTGRESQL_USER.lastname eq newLastname
                where POSTGRESQL_USER.id `in` ids
                ).execute()

    suspend fun updateAlias(newAlias: String?) =
        (sqlClient update POSTGRESQL_USER
                set POSTGRESQL_USER.alias eq newAlias
                where POSTGRESQL_USER.id eq userBboss.id
                ).execute()

    suspend fun updateWithJoin(newLastname: String, roleLabel: String) =
        (sqlClient update POSTGRESQL_USER
                set POSTGRESQL_USER.lastname eq newLastname
                innerJoin POSTGRESQL_ROLE on POSTGRESQL_USER.roleId eq POSTGRESQL_ROLE.id
                where POSTGRESQL_ROLE.label eq roleLabel
                ).execute()
}
