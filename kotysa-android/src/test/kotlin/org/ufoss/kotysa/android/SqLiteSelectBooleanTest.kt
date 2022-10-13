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
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SqLiteSelectBooleanTest : AbstractSqLiteTest<UserRepositoryBooleanSelect>(),
    SelectBooleanTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositoryBooleanSelect,
            AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositoryBooleanSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss - Android`() {
        `Verify selectAllByIsAdminEq true finds Big Boss`()
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John - Android`() {
        `Verify selectAllByIsAdminEq false finds John`()
    }
}

class UserRepositoryBooleanSelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectBooleanRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles
)
