/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SQLITE_ROLE
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod

class SqLiteSelectOrTest : AbstractSqLiteTest<UserRepositorySelectOr>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositorySelectOr(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod`() {
        assertThat(repository.selectRolesByLabels(roleAdmin.label, roleGod.label))
                .hasSize(2)
                .containsExactlyInAnyOrder(roleAdmin, roleGod)
    }
}

class UserRepositorySelectOr(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectRolesByLabels(label1: String, label2: String) =
            (sqlClient selectFrom SQLITE_ROLE
                    where SQLITE_ROLE.label eq label1
                    or SQLITE_ROLE.label eq label2
                    ).fetchAll()
}
