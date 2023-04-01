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
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SqLiteUpdateDeleteTest : AbstractSqLiteTest<UserRepositoryUpdateDelete>(),
    UpdateDeleteTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositoryUpdateDelete, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositoryUpdateDelete(dbHelper, sqLiteTables)

    @Test
    fun `Verify deleteAllFromUserRoles works correctly - Android`() {
        `Verify deleteAllFromUserRoles works correctly`()
    }

    @Test
    fun `Verify deleteUserById works - Android`() {
        `Verify deleteUserById works`()
    }

    @Test
    fun `Verify deleteUserIn works - Android`() {
        `Verify deleteUserIn works`()
    }

    @Test
    fun `Verify deleteUserIn no match - Android`() {
        `Verify deleteUserIn no match`()
    }

    @Test
    fun `Verify deleteUserWithJoin works - Android`() {
        `Verify deleteUserWithJoin works`()
    }

    @Test
    fun `Verify updateLastname works - Android`() {
        `Verify updateLastname works`()
    }

    @Test
    fun `Verify updateLastnameIn works - Android`() {
        `Verify updateLastnameIn works`()
    }

    @Test
    fun `Verify updateLastnameIn no match - Android`() {
        `Verify updateLastnameIn no match`()
    }

    @Test
    fun `Verify updateAlias works - Android`() {
        `Verify updateAlias works`()
    }

    @Test
    fun `Verify updateWithJoin works - Android`() {
        `Verify updateWithJoin works`()
    }

    @Test
    fun `Verify updateAndIncrementRoleId works - Android`() {
        `Verify updateAndIncrementRoleId works`()
    }

    @Test
    fun `Verify updateAndDecrementRoleId works - Android`() {
        `Verify updateAndDecrementRoleId works`()
    }
}

class UserRepositoryUpdateDelete(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : UpdateDeleteRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles
)
