/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SQLITE_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class SqLiteSelectBooleanTest : AbstractSqLiteTest<UserRepositoryBooleanSelect>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositoryBooleanSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
                .hasSize(1)
                .containsExactly(userJdoe)
    }
}

class UserRepositoryBooleanSelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectAllByIsAdminEq(value: Boolean) =
            (sqlClient selectFrom SQLITE_USER
                    where SQLITE_USER.isAdmin eq value
                    ).fetchAll()
}
