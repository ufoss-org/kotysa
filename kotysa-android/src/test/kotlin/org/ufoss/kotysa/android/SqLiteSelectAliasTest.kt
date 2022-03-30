/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*

class SqLiteSelectAliasTest : AbstractSqLiteTest<UserRepositorySelectAlias>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositorySelectAlias(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameGet())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAlias())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryGet())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryAlias())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }
}

class UserRepositorySelectAlias(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                where SqliteUsers.firstname["fn"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                where QueryAlias<String>("fn") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct SqliteRoles.label
                andCaseWhenExists {
            (this select SqliteUsers.id
                    from SqliteUsers
                    where SqliteUsers.roleId eq SqliteRoles.id
                    and SqliteUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from SqliteRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                orderByAsc SqliteUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                orderByAsc QueryAlias<String>("fn")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount SqliteUsers.id and SqliteUsers.roleId `as` "roleId"
                from SqliteUsers
                groupBy SqliteUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount SqliteUsers.id and SqliteUsers.roleId `as` "roleId"
                from SqliteUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()
}
