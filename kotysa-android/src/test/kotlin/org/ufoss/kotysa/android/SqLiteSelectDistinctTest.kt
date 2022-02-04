/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SqliteRoles
import org.ufoss.kotysa.test.roleAdmin
import org.ufoss.kotysa.test.roleGod
import org.ufoss.kotysa.test.roleUser

class SqLiteSelectDistinctTest : AbstractSqLiteTest<UserRepositorySelectDistinct>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositorySelectDistinct(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates`() {
        assertThat(repository.selectDistinctRoleLabels())
                .hasSize(3)
                .containsExactlyInAnyOrder(roleUser.label, roleAdmin.label, roleGod.label)
    }
}

class UserRepositorySelectDistinct(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectDistinctRoleLabels() =
            (sqlClient selectDistinct SqliteRoles.label
                    from SqliteRoles
                    ).fetchAll()
}
