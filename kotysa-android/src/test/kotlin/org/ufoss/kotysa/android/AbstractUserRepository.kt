/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

abstract class AbstractUserRepository(
    private val sqLiteOpenHelper: SQLiteOpenHelper,
    tables: Tables
) : Repository {

    protected val sqlClient = sqLiteOpenHelper.sqlClient(tables)

    override fun init() {
        createTable()
        insertRoles()
        insertUsers()
    }

    override fun delete() {
        deleteAllFromUsers()
        deleteAllFromRoles()
        sqLiteOpenHelper.close()
    }

    private fun createTable() {
        sqlClient.createTable(SQLITE_ROLE)
        sqlClient.createTable(SQLITE_USER)
    }

    private fun insertRoles() {
        sqlClient.insert(roleUser, roleAdmin, roleGod)
    }

    private fun insertUsers() {
        sqlClient.insert(userJdoe, userBboss)
    }

    fun deleteAllFromUsers() = sqlClient.deleteAllFrom(SQLITE_USER)

    private fun deleteAllFromRoles() = sqlClient.deleteAllFrom(SQLITE_ROLE)

    fun selectAll() = sqlClient.selectAllFrom(SQLITE_USER)

    /*fun selectFirstByFirstname(firstname: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] eq firstname }
            .fetchFirstOrNull()*/
}
