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
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SqLiteSelectTest : AbstractSqLiteTest<UserRepositorySelect>(),
    SelectTest<SqliteRoles, SqliteUsers, SqliteUserRoles, UserRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllUsers returns all users - Android`() {
        `Verify selectAllUsers returns all users`()
    }

    @Test
    fun `Verify countAllUsersAndAliases returns all users' count - Android`() {
        `Verify countAllUsersAndAliases returns all users' count`()
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException - Android`() {
        `Verify selectOneNonUnique throws NonUniqueResultException`()
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping - Android`() {
        `Verify selectAllMappedToDto does the mapping`()
    }

    @Test
    fun `Verify selectWithJoin works correctly - Android`() {
        `Verify selectWithJoin works correctly`()
    }

    @Test
    fun `Verify selectWithEqJoin works correctly - Android`() {
        `Verify selectWithEqJoin works correctly`()
    }

    @Test
    fun `Verify selectAllStream returns all users - Android`() {
        `Verify selectAllStream returns all users`()
    }

    @Test
    fun `Verify selectAllIn returns TheBoss - Android`() {
        `Verify selectAllIn returns TheBoss`()
    }

    @Test
    fun `Verify selectAllIn returns no result - Android`() {
        `Verify selectAllIn returns no result`()
    }

    @Test
    fun `Verify selectOneById returns TheBoss - Android`() {
        `Verify selectOneById returns TheBoss`()
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException - Android`() {
        `Verify selectOneById finds no result for -1, throws NoResultException`()
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname - Android`() {
        `Verify selectFirstnameById returns TheBoss firstname`()
    }

    @Test
    fun `Verify selectAliasById returns null as J Doe alias - Android`() {
        `Verify selectAliasById returns null as J Doe alias`()
    }

    @Test
    fun `Verify selectFirstnameAndAliasById returns J Doe firstname and alias - Android`() {
        `Verify selectFirstnameAndAliasById returns J Doe firstname and alias`()
    }

    @Test
    fun `Verify selectAllFirstnameAndAlias returns all users firstname and alias - Android`() {
        `Verify selectAllFirstnameAndAlias returns all users firstname and alias`()
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias - Android`() {
        `Verify selectFirstnameAndLastnameAndAliasById returns J Doe firstname, lastname and alias`()
    }

    @Test
    fun `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin - Android`() {
        `Verify selectFirstnameAndLastnameAndAliasAndIsAdminById returns J Doe firstname, lastname, alias and isAdmin`()
    }

    @Test
    fun `Verify selectRoleLabelFromUserId returns Admin role for TheBoss - Android`() {
        `Verify selectRoleLabelFromUserId returns Admin role for TheBoss`()
    }

    @Test
    fun `Verify countAllUsers returns 2 - Android`() {
        `Verify countAllUsers returns 2`()
    }

    @Test
    fun `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss - Android`() {
        `Verify selectRoleLabelsFromUserId returns Admin role for TheBoss`()
    }
}

class UserRepositorySelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectRepository<SqliteRoles, SqliteUsers, SqliteUserRoles>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles
)
