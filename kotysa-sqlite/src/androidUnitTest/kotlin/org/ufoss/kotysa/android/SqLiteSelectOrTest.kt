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
import org.ufoss.kotysa.test.SqliteCompanies
import org.ufoss.kotysa.test.repositories.blocking.SelectOrRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrTest

class SqLiteSelectOrTest : AbstractSqLiteTest<UserRepositorySelectOr>(),
    SelectOrTest<SqliteRoles, SqliteUsers, SqliteUserRoles, SqliteCompanies, UserRepositorySelectOr,
            AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySelectOr(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectRolesByLabels finds roleAdmin and roleGod - Android`() {
        `Verify selectRolesByLabels finds roleAdmin and roleGod`()
    }
}

class UserRepositorySelectOr(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectOrRepository<SqliteRoles, SqliteUsers, SqliteUserRoles, SqliteCompanies>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles,
    SqliteCompanies
)
