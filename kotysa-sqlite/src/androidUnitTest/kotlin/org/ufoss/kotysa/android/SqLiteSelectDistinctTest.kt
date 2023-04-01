/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteRoles
import org.ufoss.kotysa.test.SqliteUserRoles
import org.ufoss.kotysa.test.SqliteUsers
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class SqLiteSelectDistinctTest : AbstractSqLiteTest<UserRepositorySelectDistinct>(),
    SelectDistinctTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositorySelectDistinct, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySelectDistinct(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectDistinctRoleLabels finds no duplicates - Android`() {
        `Verify selectDistinctRoleLabels finds no duplicates`()
    }
}

class UserRepositorySelectDistinct(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectDistinctRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles
)
