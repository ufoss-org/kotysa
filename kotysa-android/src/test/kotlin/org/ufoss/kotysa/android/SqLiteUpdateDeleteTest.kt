/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.android.transaction.transactionalOp
import org.ufoss.kotysa.test.*

class SqLiteUpdateDeleteTest : AbstractSqLiteTest<UserRepositoryUpdateDelete>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositoryUpdateDelete(dbHelper, sqLiteTables)

    @Test
    fun `Verify deleteAllFromUser works correctly`() {
        val operator = client.transactionalOp()
        operator.execute { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteAllFromUsers())
                .isEqualTo(2)
            assertThat(repository.selectAll())
                .isEmpty()
        }
        assertThat(repository.selectAll())
            .hasSize(2)
    }

    @Test
    fun `Verify deleteUserById works`() {
        val operator = client.transactionalOp()
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserById(sqLiteJdoe.id))
                .isEqualTo(1)
            assertThat(repository.selectAll())
                .hasSize(1)
        }
    }

    @Test
    fun `Verify deleteUserWithJoin works`() {
        val operator = client.transactionalOp()
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.deleteUserWithJoin(sqLiteUser.label))
                .isEqualTo(1)
            assertThat(repository.selectAll())
                .hasSize(1)
                .containsOnly(sqLiteBboss)
        }
    }

    @Test
    fun `Verify updateLastname works`() {
        val operator = client.transactionalOp()
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateLastname("Do"))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(sqLiteJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }

    @Test
    fun `Verify updateWithJoin works`() {
        val operator = client.transactionalOp()
        operator.execute<Unit> { transaction ->
            transaction.setRollbackOnly()
            assertThat(repository.updateWithJoin("Do", sqLiteUser.label))
                .isEqualTo(1)
            assertThat(repository.selectFirstByFirstame(sqLiteJdoe.firstname))
                .extracting { user -> user?.lastname }
                .isEqualTo("Do")
        }
    }
}

class UserRepositoryUpdateDelete(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun deleteUserById(id: String) =
        sqlClient.deleteFromTable<SqLiteUser>()
            .where { it[SqLiteUser::id] eq id }
            .execute()

    fun deleteUserWithJoin(roleLabel: String) =
        sqlClient.deleteFromTable<SqLiteUser>()
            .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
            .where { it[SqLiteRole::label] eq roleLabel }
            .execute()

    fun updateLastname(newLastname: String) =
        sqlClient.updateTable<SqLiteUser>()
            .set { it[SqLiteUser::lastname] = newLastname }
            .where { it[SqLiteUser::id] eq sqLiteJdoe.id }
            .execute()

    fun updateWithJoin(newLastname: String, roleLabel: String) =
        sqlClient.updateTable<SqLiteUser>()
            .set { it[SqLiteUser::lastname] = newLastname }
            .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
            .where { it[SqLiteRole::label] eq roleLabel }
            .execute()
}
