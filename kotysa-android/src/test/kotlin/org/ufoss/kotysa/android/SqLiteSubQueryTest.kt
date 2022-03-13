/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

class SqLiteSubQueryTest : AbstractSqLiteTest<UserRepositorySqliteSubQuery>() {
    override fun getRepository(sqLiteTables: Tables) = UserRepositorySqliteSubQuery(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`() {
        assertThat(repository.selectRoleLabelWhereExistsUserSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(roleAdmin.label, roleUser.label)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results`() {
        assertThat(repository.selectCaseWhenExistsSubQuery(listOf(userBboss.id, userJdoe.id)))
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(true, roleAdmin.label), Pair(true, roleUser.label))
    }
}


class UserRepositorySqliteSubQuery(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select SqliteUsers.firstname and {
            (this select SqliteRoles.label
                    from SqliteRoles
                    where SqliteRoles.id eq SqliteUsers.roleId
                    and SqliteRoles.label eq roleAdmin.label)
        }
                from SqliteUsers
                where SqliteUsers.id eq userId)
            .fetchOne()

    fun selectRoleLabelWhereExistsUserSubQuery(userIds: List<Int>) =
        (sqlClient select SqliteRoles.label
                from SqliteRoles
        whereExists 
        {
            (this select SqliteUsers.id
                    from SqliteUsers
                    where SqliteUsers.roleId eq SqliteRoles.id
                    and SqliteUsers.id `in` userIds)
        })
            .fetchAll()

    fun selectCaseWhenExistsSubQuery(userIds: List<Int>) =
        (sqlClient selectCaseWhenExists
        {
            (this select SqliteUsers.id
                    from SqliteUsers
                    where SqliteUsers.roleId eq SqliteRoles.id
                    and SqliteUsers.id `in` userIds)
        } then true `else` false // `as` "roleUsedByUser"
                and SqliteRoles.label
                from SqliteRoles)
            .fetchAll()
}
