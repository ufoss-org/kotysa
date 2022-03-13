/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*

class SqLiteSelectTest : AbstractSqLiteTest<UserRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositorySelect(dbHelper, sqLiteTables)

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
    fun `Verify selectWithEqJoin works correctly`() {
        assertThat(repository.selectWithEqJoin())
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
    fun `Verify selectAliasedFirstnameByFirstname returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstname(userBboss.firstname))
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

class UserRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectOneNonUnique() =
            (sqlClient selectFrom SqliteUsers
                    ).fetchOne()

    fun selectAllMappedToDto() =
            (sqlClient selectAndBuild {
                UserDto("${it[SqliteUsers.firstname]} ${it[SqliteUsers.lastname]}", it[SqliteUsers.alias])
            }
                    from SqliteUsers
                    ).fetchAll()

    fun selectWithJoin() =
            (sqlClient selectAndBuild { UserWithRoleDto(it[SqliteUsers.lastname]!!, it[SqliteRoles.label]!!) }
                    from SqliteUsers innerJoin SqliteRoles on SqliteUsers.roleId eq SqliteRoles.id
                    ).fetchAll()

    fun selectWithEqJoin() =
        (sqlClient selectAndBuild { UserWithRoleDto(it[SqliteUsers.lastname]!!, it[SqliteRoles.label]!!) }
                from SqliteUsers and SqliteRoles
                where SqliteUsers.roleId eq SqliteRoles.id
                ).fetchAll()

    fun selectAllStream() =
            (sqlClient selectFrom SqliteUsers
                    ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
            (sqlClient selectFrom SqliteUsers
                    where SqliteUsers.alias `in` aliases
                    ).fetchAll()

    fun selectOneById(id: Int) =
            (sqlClient select SqliteUsers
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOne()

    fun selectFirstnameById(id: Int) =
            (sqlClient select SqliteUsers.firstname
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOne()

    fun selectAliasedFirstnameByFirstname(firstname: String) =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                where SqliteUsers.firstname["fn"] eq firstname
                ).fetchOne()

    fun selectAliasById(id: Int) =
            (sqlClient select SqliteUsers.alias
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOneOrNull()

    fun selectFirstnameAndAliasById(id: Int) =
            (sqlClient select SqliteUsers.firstname and SqliteUsers.alias
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOne()

    fun selectAllFirstnameAndAlias() =
            (sqlClient select SqliteUsers.firstname and SqliteUsers.alias
                    from SqliteUsers
                    ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
            (sqlClient select SqliteUsers.firstname and SqliteUsers.lastname and SqliteUsers.alias
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
            (sqlClient select SqliteUsers.firstname and SqliteUsers.lastname and SqliteUsers.alias and SqliteUsers.isAdmin
                    from SqliteUsers
                    where SqliteUsers.id eq id
                    ).fetchOne()

    fun countAllUsersAndAliases() =
            (sqlClient selectCount SqliteUsers.id
                    andCount SqliteUsers.alias
                    from SqliteUsers
                    ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
            (sqlClient select SqliteRoles.label
                    from SqliteRoles innerJoin SqliteUsers on SqliteRoles.id eq SqliteUsers.roleId
                    where SqliteUsers.id eq userId)
                    .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom SqliteUsers

    fun selectRoleLabelsFromUserId(userId: Int) =
            (sqlClient select SqliteRoles.label
                    from SqliteUserRoles innerJoin SqliteRoles on SqliteUserRoles.roleId eq SqliteRoles.id
                    where SqliteUserRoles.userId eq userId)
                    .fetchAll()
}
