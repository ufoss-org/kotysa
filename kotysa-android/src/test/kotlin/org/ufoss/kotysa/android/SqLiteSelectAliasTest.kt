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
}
