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
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SqLiteSelectStringTest : AbstractSqLiteTest<UserRepositoryStringSelect>(),
    SelectStringTest<SqliteRoles, SqliteUsers, SqliteUserRoles, SqliteCompanies, UserRepositoryStringSelect,
            AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositoryStringSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectFirstByFirstname finds John - Android`() {
        `Verify selectFirstByFirstname finds John`()
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown - Android`() {
        `Verify selectFirstByFirstname finds no Unknown`()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException - Android`() {
        `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`()
    }

    @Test
    fun `Verify selectByAlias finds BigBoss - Android`() {
        `Verify selectByAlias finds BigBoss`()
    }

    @Test
    fun `Verify selectByAlias with null alias finds John - Android`() {
        `Verify selectByAlias with null alias finds John`()
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John - Android`() {
        `Verify selectAllByFirstnameNotEq ignore John`()
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow - Android`() {
        `Verify selectAllByFirstnameNotEq ignore unknow`()
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss - Android`() {
        `Verify selectAllByAliasNotEq ignore BigBoss`()
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss - Android`() {
        `Verify selectAllByAliasNotEq with null alias finds BigBoss`()
    }

    @Test
    fun `Verify selectAllByFirstnameIn finds John and BigBoss - Android`() {
        `Verify selectAllByFirstnameIn finds John and BigBoss`()
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh - Android`() {
        `Verify selectAllByFirstnameContains get John by searching oh`()
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz - Android`() {
        `Verify selectAllByFirstnameContains get nothing by searching boz`()
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh - Android`() {
        `Verify selectAllByFirstnameStartsWith get John by searching Joh`()
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh - Android`() {
        `Verify selectAllByFirstnameStartsWith get nothing by searching oh`()
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn - Android`() {
        `Verify selectAllByFirstnameEndsWith get John by searching ohn`()
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh - Android`() {
        `Verify selectAllByFirstnameEndsWith get nothing by searching joh`()
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos - Android`() {
        `Verify selectAllByAliasContains get Boss by searching heBos`()
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz - Android`() {
        `Verify selectAllByAliasContains get nothing by searching heBoz`()
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo - Android`() {
        `Verify selectAllByAliasStartsWith get Boss by searching TheBo`()
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo - Android`() {
        `Verify selectAllByAliasStartsWith get nothing by searching heBo`()
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss - Android`() {
        `Verify selectAllByAliasEndsWith get Boss by searching Boss`()
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo - Android`() {
        `Verify selectAllByAliasEndsWith get nothing by searching TheBo`()
    }
}

class UserRepositoryStringSelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectStringRepository<SqliteRoles, SqliteUsers, SqliteUserRoles, SqliteCompanies>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles,
    SqliteCompanies
)
