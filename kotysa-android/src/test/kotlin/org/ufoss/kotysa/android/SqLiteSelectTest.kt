/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

class SqLiteSelectTest : AbstractSqLiteTest<UserRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) =
            UserRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAll())
                .hasSize(2)
                .containsExactlyInAnyOrder(sqLiteJdoe, sqLiteBboss)
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique() }
                .isInstanceOf(NonUniqueResultException::class.java)
    }

    @Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserDto("John Doe", null),
                        UserDto("Big Boss", "TheBoss"))
    }

    @Test
    fun `Verify selectWithJoin works correctly`() {
        assertThat(repository.selectWithJoin())
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        UserWithRoleDto(sqLiteJdoe.lastname, sqLiteUser.label),
                        UserWithRoleDto(sqLiteBboss.lastname, sqLiteAdmin.label)
                )
    }
}

class UserRepositorySelect(sqLiteOpenHelper: SQLiteOpenHelper, tables: Tables) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectOneNonUnique() = sqlClient.select<SqLiteUser>()
            .fetchOne()

    fun selectAllMappedToDto() =
            sqlClient.select {
                UserDto("${it[SqLiteUser::firstname]} ${it[SqLiteUser::lastname]}",
                        it[SqLiteUser::alias])
            }.fetchAll()

    fun selectWithJoin() =
            sqlClient.select { UserWithRoleDto(it[SqLiteUser::lastname], it[SqLiteRole::label]) }
                    .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
                    .fetchAll()
}
