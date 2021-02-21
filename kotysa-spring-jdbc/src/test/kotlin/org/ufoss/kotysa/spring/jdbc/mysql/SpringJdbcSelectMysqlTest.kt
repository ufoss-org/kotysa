/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectMysqlTest : AbstractSpringJdbcMysqlTest<UserRepositorySpringJdbcMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositorySpringJdbcMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases())
                .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique() }
                .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss")
                )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(userJdoe.lastname, roleUser.label),
                        UserWithRoleDto(userBboss.lastname, roleAdmin.label)
                )
    }

    @Test
    fun `Verify selectAllStream returns all users`() {
        assertThat(repository.selectAllStream())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")))
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll))
                .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id))
                .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() {
        assertThatThrownBy { repository.selectOneById(-1) }
                .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id))
                .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        assertThat(repository.selectAliasById(userJdoe.id))
                .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id))
                .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        Pair(userJdoe.firstname, null),
                        Pair(userBboss.firstname, userBboss.alias),
                )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id))
                .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id))
                .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleNameFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleNameFromUserId(userBboss.id))
                .isEqualTo(roleAdmin.label)
    }
}


class UserRepositorySpringJdbcMysqlSelect(client: JdbcOperations) : AbstractUserRepositorySpringJdbcMysql(client) {

    fun selectOneNonUnique() =
            (sqlClient selectFrom MYSQL_USER
                    ).fetchOne()

    fun selectAllMappedToDto() =
            (sqlClient select {
                UserDto("${it[MYSQL_USER.firstname]} ${it[MYSQL_USER.lastname]}", it[MYSQL_USER.alias])
            }
                    from MYSQL_USER
                    ).fetchAll()

    fun selectWithJoin() =
            (sqlClient select { UserWithRoleDto(it[MYSQL_USER.lastname]!!, it[MYSQL_ROLE.label]!!) }
                    from MYSQL_USER innerJoin MYSQL_ROLE on MYSQL_USER.roleId eq MYSQL_ROLE.id
                    ).fetchAll()

    fun selectAllStream() =
            (sqlClient selectFrom MYSQL_USER
                    ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
            (sqlClient selectFrom MYSQL_USER
                    where MYSQL_USER.alias `in` aliases
                    ).fetchAll()

    fun selectOneById(id: Int) =
            (sqlClient select MYSQL_USER
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameById(id: Int) =
            (sqlClient select MYSQL_USER.firstname
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOne()

    fun selectAliasById(id: Int) =
            (sqlClient select MYSQL_USER.alias
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOneOrNull()

    fun selectFirstnameAndAliasById(id: Int) =
            (sqlClient select MYSQL_USER.firstname and MYSQL_USER.alias
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOne()

    fun selectAllFirstnameAndAlias() =
            (sqlClient select MYSQL_USER.firstname and MYSQL_USER.alias
                    from MYSQL_USER
                    ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
            (sqlClient select MYSQL_USER.firstname and MYSQL_USER.lastname and MYSQL_USER.alias
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
            (sqlClient select MYSQL_USER.firstname and MYSQL_USER.lastname and MYSQL_USER.alias and MYSQL_USER.isAdmin
                    from MYSQL_USER
                    where MYSQL_USER.id eq id
                    ).fetchOne()

    fun countAllUsersAndAliases() =
            (sqlClient selectCount MYSQL_USER.id
                    andCount MYSQL_USER.alias
                    from MYSQL_USER
                    ).fetchOne()

    fun selectRoleNameFromUserId(userId: Int) =
            (sqlClient select MYSQL_ROLE.label
                    from MYSQL_ROLE innerJoin MYSQL_USER on MYSQL_ROLE.id eq MYSQL_USER.roleId
                    where MYSQL_USER.id eq userId)
                    .fetchOne()
}
