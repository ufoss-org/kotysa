/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepository(
    private val sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
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
    }

    private fun createTable() {
        sqlClient createTableIfNotExists SqliteRoles
        sqlClient createTableIfNotExists SqliteUsers
        sqlClient createTableIfNotExists SqliteUserRoles
    }

    private fun insertRoles() {
        val operator = sqLiteOpenHelper.writableDatabase.transactionalOp()
        operator.transactional {
            sqlClient.insert(roleUser, roleAdmin, roleGod)
        }
    }

    private fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    private fun insertUserRoles() {
        sqlClient insert userRoleBboss
    }

    private fun deleteAllFromUsers() = sqlClient deleteAllFrom SqliteUsers

    private fun deleteAllFromRoles() = sqlClient deleteAllFrom SqliteRoles

    fun deleteAllFromUserRoles() = sqlClient deleteAllFrom SqliteUserRoles

    fun countAllUserRoles() = sqlClient selectCountAllFrom SqliteUserRoles

    fun selectAllUsers() = sqlClient selectAllFrom SqliteUsers

    fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom SqliteUsers
                    where SqliteUsers.firstname eq firstname
                    ).fetchFirstOrNull()
}
