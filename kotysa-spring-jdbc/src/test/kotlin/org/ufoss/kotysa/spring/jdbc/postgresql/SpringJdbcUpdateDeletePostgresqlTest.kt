/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcUpdateDeletePostgresqlTest : AbstractSpringJdbcPostgresqlTest<UserRepositorySpringJdbcPostgresqlUpdateDelete>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcPostgresqlUpdateDelete>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUsers())
                    .isEqualTo(2)
            assertThat(repository.selectAllUsers())
                    .isEmpty()
        }
    }

    @Test
    fun `Verify deleteUserById works`() {
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
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
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", roleUser.label))
                    .isEqualTo(1)
            assertThat(repository.selectFirstByFirstname(userJdoe.firstname))
                    .extracting { user -> user?.lastname }
                    .isEqualTo("Do")
        }
    }
}


class UserRepositorySpringJdbcPostgresqlUpdateDelete(client: JdbcOperations) : AbstractUserRepositorySpringJdbcPostgresql(client) {

    fun deleteUserById(id: Int) =
            (sqlClient deleteFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.id eq id
                    ).execute()

    fun deleteUserIn(ids: Collection<Int>) =
            (sqlClient deleteFrom POSTGRESQL_USER
                    where POSTGRESQL_USER.id `in` ids
                    ).execute()

    fun deleteUserWithJoin(roleLabel: String) =
            (sqlClient deleteFrom POSTGRESQL_USER
                    innerJoin POSTGRESQL_ROLE on POSTGRESQL_USER.roleId eq POSTGRESQL_ROLE.id
                    where POSTGRESQL_ROLE.label eq roleLabel
                    ).execute()

    fun updateLastname(newLastname: String) =
            (sqlClient update POSTGRESQL_USER
                    set POSTGRESQL_USER.lastname eq newLastname
                    where POSTGRESQL_USER.id eq userJdoe.id
                    ).execute()

    fun updateLastnameIn(newLastname: String, ids: Collection<Int>) =
            (sqlClient update POSTGRESQL_USER
                    set POSTGRESQL_USER.lastname eq newLastname
                    where POSTGRESQL_USER.id `in` ids
                    ).execute()

    fun updateAlias(newAlias: String?) =
            (sqlClient update POSTGRESQL_USER
                    set POSTGRESQL_USER.alias eq newAlias
                    where POSTGRESQL_USER.id eq userBboss.id
                    ).execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
            (sqlClient update POSTGRESQL_USER
                    set POSTGRESQL_USER.lastname eq newLastname
                    innerJoin POSTGRESQL_ROLE on POSTGRESQL_USER.roleId eq POSTGRESQL_ROLE.id
                    where POSTGRESQL_ROLE.label eq roleLabel
                    ).execute()
}
