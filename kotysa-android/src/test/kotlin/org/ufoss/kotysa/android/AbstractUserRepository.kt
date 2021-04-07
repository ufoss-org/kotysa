/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepository(
        private val sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables,
) : Repository {

    protected val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTable()
        insertRoles()
        insertUsers()
        insertUserRoles()
    }

    override fun delete() {
        deleteAllFromUserRoles()
        deleteAllFromUsers()
        deleteAllFromRoles()
        sqLiteOpenHelper.close()
    }

    private fun createTable() {
        sqlClient createTable SQLITE_ROLE
        sqlClient createTable SQLITE_USER
        sqlClient createTable SQLITE_USER_ROLE
    }

    private fun insertRoles() {
        val operator = sqLiteOpenHelper.writableDatabase.transactionalOp()
        operator.execute {
            sqlClient.insert(roleUser, roleAdmin, roleGod, roleGodBis)
        }
    }

    private fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom SQLITE_USER

    private fun deleteAllFromRoles() = sqlClient deleteAllFrom SQLITE_ROLE

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom SQLITE_USER_ROLE

    fun countAllUserRoles() = sqlClient selectCountAllFrom SQLITE_USER_ROLE

    fun selectAllUsers() = sqlClient selectAllFrom SQLITE_USER

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom SQLITE_USER
                    where SQLITE_USER.firstname eq firstname
                    ).fetchFirstOrNull()
}
