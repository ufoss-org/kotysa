/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class SqLiteUpdateDeleteTest : AbstractSqLiteTest<UserRepositoryUpdateDelete>() {

    override fun getRepository(dbHelper: DbHelper, sqLiteTables: Tables) =
            UserRepositoryUpdateDelete(dbHelper, sqLiteTables)

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        assertThat(repository.deleteAllFromUsers())
                .isEqualTo(2)
        assertThat(repository.selectAll().toList())
                .isEmpty()
        // re-insert users
        repository.insertUsers()
    }

    @Test
    fun `Verify deleteUserById works`() {
        assertThat(repository.deleteUserById(sqLiteJdoe.id))
                .isEqualTo(1)
        assertThat(repository.selectAll())
                .hasSize(1)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        assertThat(repository.deleteUserWithJoin(sqLiteUser.label))
                .isEqualTo(1)
        assertThat(repository.selectAll())
                .hasSize(1)
                .containsOnly(sqLiteBboss)
        // re-insertUsers jdoe
        repository.insertJDoe()
    }

    @Test
    fun `Verify updateLastname works`() {
        assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(sqLiteJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(sqLiteJdoe.lastname)
    }

    @Test
    fun `Verify updateWithJoin works`() {
        assertThat(repository.updateWithJoin("Do", sqLiteUser.label))
                .isEqualTo(1)
        assertThat(repository.selectFirstByFirstame(sqLiteJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        repository.updateLastname(sqLiteJdoe.lastname)
    }
}

class UserRepositoryUpdateDelete(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun deleteUserById(id: String) = sqlClient.deleteFromTable<SqLiteUser>()
            .where { it[SqLiteUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) = sqlClient.deleteFromTable<SqLiteUser>()
            .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
            .where { it[SqLiteRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) = sqlClient.updateTable<SqLiteUser>()
            .set { it[SqLiteUser::lastname] = newLastname }
            .where { it[SqLiteUser::id] eq sqLiteJdoe.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) = sqlClient.updateTable<SqLiteUser>()
            .set { it[SqLiteUser::lastname] = newLastname }
            .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
            .where { it[SqLiteRole::label] eq roleLabel }
            .execute()
}
