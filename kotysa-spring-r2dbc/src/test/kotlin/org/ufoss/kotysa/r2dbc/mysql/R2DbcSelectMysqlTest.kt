/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.r2dbc.ReactorSqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import reactor.kotlin.test.test


class R2DbcSelectMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllUsers returns all users`() {
        assertThat(repository.selectAllUsers().toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count`() {
        assertThat(repository.countAllUsersAndAliases().block())
                .isEqualTo(Pair(2L, 1L))
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique().block() }
                .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss")
                )
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin().toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(userJdoe.lastname, roleUser.label),
                        UserWithRoleDto(userBboss.lastname, roleAdmin.label)
                )
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")).toIterable())
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id).block())
                .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1`() {
        repository.selectOneById(-1)
                .test()
                .verifyComplete()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id).block())
                .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias`() {
        assertThat(repository.selectAliasById(userJdoe.id).block())
                .isNull()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`() {
        assertThat(repository.selectFirstnameAndAliasById(userJdoe.id).block())
                .isEqualTo(Pair(userJdoe.firstname, null))
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias`() {
        assertThat(repository.selectAllFirstnameAndAlias().toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        Pair(userJdoe.firstname, null),
                        Pair(userBboss.firstname, userBboss.alias),
                )
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasById(userJdoe.id).block())
                .isEqualTo(Triple(userJdoe.firstname, userJdoe.lastname, null))
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`() {
        assertThat(repository.selectFirstnameAndLastnameAndAliasAndIsAdminById(userJdoe.id).block())
                .isEqualTo(listOf(userJdoe.firstname, userJdoe.lastname, null, false))
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id).block())
                .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers().block()!!)
                .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id).toIterable())
                .hasSize(1)
                .containsExactly(roleAdmin.label)
    }

    @Test
    fun `Verify selectAllLimitOffset returns 1 user`() {
        assertThat(repository.selectAllLimitOffset().toIterable())
                .hasSize(1)
    }
}


class UserRepositoryMysqlSelect(sqlClient: ReactorSqlClient) : AbstractUserRepositoryMysql(sqlClient) {

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
                    ).fetchOne()

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

    fun selectRoleLabelFromUserId(userId: Int) =
            (sqlClient select MYSQL_ROLE.label
                    from MYSQL_ROLE innerJoin MYSQL_USER on MYSQL_ROLE.id eq MYSQL_USER.roleId
                    where MYSQL_USER.id eq userId)
                    .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom MYSQL_USER

    fun selectRoleLabelsFromUserId(userId: Int) =
            (sqlClient select MYSQL_ROLE.label
                    from MYSQL_USER_ROLE innerJoin MYSQL_ROLE on MYSQL_USER_ROLE.roleId eq MYSQL_ROLE.id
                    where MYSQL_USER_ROLE.userId eq userId)
                    .fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom MYSQL_USER
                    limit 1 offset 1
                    ).fetchAll()
}
