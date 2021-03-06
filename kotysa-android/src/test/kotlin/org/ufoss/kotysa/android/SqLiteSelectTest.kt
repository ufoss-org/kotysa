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

    @Test
    fun `Verify selectAllLimitOffset returns 1 user`() {
        assertThat(repository.selectAllLimitOffset())
                .hasSize(1)
    }
}

class UserRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectOneNonUnique() =
            (sqlClient selectFrom SQLITE_USER
                    ).fetchOne()

    fun selectAllMappedToDto() =
            (sqlClient select {
                UserDto("${it[SQLITE_USER.firstname]} ${it[SQLITE_USER.lastname]}", it[SQLITE_USER.alias])
            }
                    from SQLITE_USER
                    ).fetchAll()

    fun selectWithJoin() =
            (sqlClient select { UserWithRoleDto(it[SQLITE_USER.lastname]!!, it[SQLITE_ROLE.label]!!) }
                    from SQLITE_USER innerJoin SQLITE_ROLE on SQLITE_USER.roleId eq SQLITE_ROLE.id
                    ).fetchAll()

    fun selectAllStream() =
            (sqlClient selectFrom SQLITE_USER
                    ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
            (sqlClient selectFrom SQLITE_USER
                    where SQLITE_USER.alias `in` aliases
                    ).fetchAll()

    fun selectOneById(id: Int) =
            (sqlClient select SQLITE_USER
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameById(id: Int) =
            (sqlClient select SQLITE_USER.firstname
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectAliasById(id: Int) =
            (sqlClient select SQLITE_USER.alias
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOneOrNull()

    fun selectFirstnameAndAliasById(id: Int) =
            (sqlClient select SQLITE_USER.firstname and SQLITE_USER.alias
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectAllFirstnameAndAlias() =
            (sqlClient select SQLITE_USER.firstname and SQLITE_USER.alias
                    from SQLITE_USER
                    ).fetchAll()

    fun selectFirstnameAndLastnameAndAliasById(id: Int) =
            (sqlClient select SQLITE_USER.firstname and SQLITE_USER.lastname and SQLITE_USER.alias
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameAndLastnameAndAliasAndIsAdminById(id: Int) =
            (sqlClient select SQLITE_USER.firstname and SQLITE_USER.lastname and SQLITE_USER.alias and SQLITE_USER.isAdmin
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun countAllUsersAndAliases() =
            (sqlClient selectCount SQLITE_USER.id
                    andCount SQLITE_USER.alias
                    from SQLITE_USER
                    ).fetchOne()

    fun selectRoleLabelFromUserId(userId: Int) =
            (sqlClient select SQLITE_ROLE.label
                    from SQLITE_ROLE innerJoin SQLITE_USER on SQLITE_ROLE.id eq SQLITE_USER.roleId
                    where SQLITE_USER.id eq userId)
                    .fetchOne()

    fun countAllUsers() = sqlClient selectCountAllFrom SQLITE_USER

    fun selectRoleLabelsFromUserId(userId: Int) =
            (sqlClient select SQLITE_ROLE.label
                    from SQLITE_USER_ROLE innerJoin SQLITE_ROLE on SQLITE_USER_ROLE.roleId eq SQLITE_ROLE.id
                    where SQLITE_USER_ROLE.userId eq userId)
                    .fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom SQLITE_USER
                    limit 1 offset 1
                    ).fetchAll()
}
