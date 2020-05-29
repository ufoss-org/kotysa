/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SqLiteUser
import org.ufoss.kotysa.test.sqLiteBboss
import org.ufoss.kotysa.test.sqLiteJdoe
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SqLiteSelectBooleanTest : AbstractSqLiteTest<UserRepositoryBooleanSelect>() {

    override fun getRepository(dbHelper: DbHelper, sqLiteTables: Tables) =
            UserRepositoryBooleanSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllByIsAdminEq true finds Big Boss`() {
        assertThat(repository.selectAllByIsAdminEq(true))
                .hasSize(1)
                .containsExactly(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByIsAdminEq false finds John`() {
        assertThat(repository.selectAllByIsAdminEq(false))
                .hasSize(1)
                .containsExactly(sqLiteJdoe)
    }
}

class UserRepositoryBooleanSelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectAllByIsAdminEq(value: Boolean) = sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::isAdmin] eq value }
            .fetchAll()
}
