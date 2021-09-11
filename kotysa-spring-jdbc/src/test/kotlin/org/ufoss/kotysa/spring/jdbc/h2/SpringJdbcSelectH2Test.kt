/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.test.*


class SpringJdbcSelectH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2Select>() {
    override val context = startContext<UserRepositorySpringJdbcH2Select>()
    override val repository = getContextRepository<UserRepositorySpringJdbcH2Select>()

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
        assertThat(repository.selectAllMappedToDto())
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
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserId(userBboss.id))
                .isEqualTo(roleAdmin.label)
    }

    @Test
    fun `Verify countAllUsers returns 2`() {
        assertThat(repository.countAllUsers())
                .isEqualTo(2L)
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelsFromUserId(userBboss.id))
                .hasSize(1)
                .containsExactly(roleAdmin.label)
    }
}


class UserRepositorySpringJdbcH2Select(client: JdbcOperations) : AbstractUserRepositorySpringJdbcH2(client) {

    fun selectOneNonUnique() =
            (sqlClient selectFrom H2_USER
                    ).fetchOne()

    fun selectAllMappedToDto() =
            (sqlClient select {
                UserDto("${it[H2_USER.firstname]} ${it[H2_USER.lastname]}", it[H2_USER.alias])
            }
                    from H2_USER
                    ).fetchAll()

    fun selectWithJoin() =
            (sqlClient select { UserWithRoleDto(it[H2_USER.lastname]!!, it[H2_ROLE.label]!!) }
                    from H2_USER innerJoin H2_ROLE on H2_USER.roleId eq H2_ROLE.id
                    ).fetchAll()

    fun selectAllStream() =
            (sqlClient selectFrom H2_USER
                    ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
            (sqlClient selectFrom H2_USER
                    where H2_USER.alias `in` aliases
                    ).fetchAll()

    fun selectOneById(id: Int) =
            (sqlClient select H2_USER
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameById(id: Int) =
            (sqlClient select H2_USER.firstname
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOne()

    fun selectAliasById(id: Int) =
            (sqlClient select H2_USER.alias
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOneOrNull()

    fun selectFirstnameAndAliasById(id: Int) =
            (sqlClient select H2_USER.firstname and H2_USER.alias
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOne()

    fun selectAllFirstnameAndAlias() =
            (sqlClient select H2_USER.firstname and H2_USER.alias
                    from H2_USER
                    ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
            (sqlClient select H2_USER.firstname and H2_USER.lastname and H2_USER.alias
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
            (sqlClient select H2_USER.firstname and H2_USER.lastname and H2_USER.alias and H2_USER.isAdmin
                    from H2_USER
                    where H2_USER.id eq id
                    ).fetchOne()

    fun countAllUsersAndAliases() =
            (sqlClient selectCount H2_USER.id
                    andCount H2_USER.alias
                    from H2_USER
                    ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
            (sqlClient select H2_ROLE.label
                    from H2_ROLE innerJoin H2_USER on H2_ROLE.id eq H2_USER.roleId
                    where H2_USER.id eq userId)
                    .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom H2_USER

    fun selectRoleLabelsFromUserId(userId: Int) =
            (sqlClient select H2_ROLE.label
                    from H2_USER_ROLE innerJoin H2_ROLE on H2_USER_ROLE.roleId eq H2_ROLE.id
                    where H2_USER_ROLE.userId eq userId)
                    .fetchAll()
}
