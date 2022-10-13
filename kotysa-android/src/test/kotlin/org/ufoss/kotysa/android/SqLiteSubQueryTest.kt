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
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SqLiteSubQueryTest : AbstractSqLiteTest<UserRepositorySqliteSubQuery>(),
    SubQueryTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositorySqliteSubQuery, AndroidTransaction> {
    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySqliteSubQuery(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss - Android`() {
        `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`()
    }

    @Test
    fun `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles - Android`() {
        `Verify selectRoleLabelWhereExistsUserSubQuery returns User and Admin roles`()
    }

    @Test
    fun `Verify selectRoleLabelWhereEqUserSubQuery returns User role - Android`() {
        `Verify selectRoleLabelWhereEqUserSubQuery returns User role`()
    }

    @Test
    fun `Verify selectRoleLabelAndEqUserSubQuery returns User role - Android`() {
        `Verify selectRoleLabelAndEqUserSubQuery returns User role`()
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles - Android`() {
        `Verify selectRoleLabelWhereInUserSubQuery returns User and Admin roles`()
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQuery returns results - Android`() {
        `Verify selectCaseWhenExistsSubQuery returns results`()
    }

    @Test
    fun `Verify selectOrderByCaseWhenExistsSubQuery returns results - Android`() {
        `Verify selectOrderByCaseWhenExistsSubQuery returns results`()
    }
}

class UserRepositorySqliteSubQuery(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SubQueryRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles
)
