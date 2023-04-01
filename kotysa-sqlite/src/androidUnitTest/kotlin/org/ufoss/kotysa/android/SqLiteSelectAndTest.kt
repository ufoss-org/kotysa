/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SqliteSelectAndTest : AbstractSqLiteTest<UserRepositorySelectAnd>(),
    SelectAndTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositorySelectAnd,
            AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySelectAnd(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectRolesByLabels finds roleUser - Android`() {
        `Verify selectRolesByLabels finds roleUser`()
    }
}

class UserRepositorySelectAnd(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) :
    SelectAndRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
        sqLiteOpenHelper.sqlClient(tables),
        SqliteRoles, SqliteUsers, SqliteUserRoles
    )
