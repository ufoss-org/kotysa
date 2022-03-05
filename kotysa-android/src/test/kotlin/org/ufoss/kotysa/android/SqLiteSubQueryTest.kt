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
}
