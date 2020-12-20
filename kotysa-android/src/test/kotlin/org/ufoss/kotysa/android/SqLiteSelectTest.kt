/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.NonUniqueResultException
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SQLITE_USER
import org.ufoss.kotysa.test.userBboss
import org.ufoss.kotysa.test.userJdoe

class SqLiteSelectTest : AbstractSqLiteTest<UserRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAll returns all users`() {
        assertThat(repository.selectAll())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectOneNonUnique throws NonUniqueResultException`() {
        assertThatThrownBy { repository.selectOneNonUnique() }
            .isInstanceOf(NonUniqueResultException::class.java)
    }

    /*@Test
    fun `Verify selectAllMappedToDto does the mapping`() {
        assertThat(repository.selectAllMappedToDto().toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                UserDto("John Doe", null),
                UserDto("Big Boss", "TheBoss")
            )
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
     */

    @Test
    fun `Verify selectAllStream returns all users`() {
        assertThat(repository.selectAllStream())
                .hasSize(2)
                .containsExactlyInAnyOrder(userJdoe, userBboss)
    }

    @Test
    fun `Verify selectAllIn returns TheBoss`() {
        assertThat(repository.selectAllIn(setOf("TheBoss", "TheStar", "TheBest")))
                .hasSize(1)
                .containsExactly(userBboss)
    }

    @Test
    fun `Verify selectAllIn returns no result`() {
        val coll = ArrayDeque<String>()
        coll.addFirst("TheStar")
        coll.addLast("TheBest")
        assertThat(repository.selectAllIn(coll))
                .isEmpty()
    }

    @Test
    fun `Verify selectOneById returns TheBoss`() {
        assertThat(repository.selectOneById(userBboss.id))
                .isEqualTo(userBboss)
    }

    @Test
    fun `Verify selectOneById finds no result for -1, throws NoResultException`() {
        assertThatThrownBy { repository.selectOneById(-1) }
                .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectFirstnameById returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameById(userBboss.id))
                .isEqualTo(userBboss.firstname)
    }
}

class UserRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectOneNonUnique() =
            (sqlClient selectFrom SQLITE_USER
                    ).fetchOne()

    /*fun selectAllMappedToDto() =
        sqlClient.select {
            UserDto(
                "${it[SqLiteUser::firstname]} ${it[SqLiteUser::lastname]}",
                it[SqLiteUser::alias]
            )
        }.fetchAll()

    fun selectWithJoin() =
        sqlClient.select { UserWithRoleDto(it[SqLiteUser::lastname], it[SqLiteRole::label]) }
            .innerJoin<SqLiteRole>().on { it[SqLiteUser::roleId] }
            .fetchAll()

     */

    fun selectAllStream() =
            (sqlClient selectFrom SQLITE_USER
                    ).fetchAllStream()

    fun selectAllIn(aliases: Collection<String>) =
            (sqlClient selectFrom SQLITE_USER
                    where SQLITE_USER.alias `in` aliases
                    ).fetchAll()

    fun selectOneById(id: Int) =
            (sqlClient select SQLITE_USER
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameById(id: Int) =
            (sqlClient select SQLITE_USER.firstname
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()

    fun selectFirstnameAndAliasById(id: Int) =
            (sqlClient select SQLITE_USER.firstname and SQLITE_USER.alias
                    from SQLITE_USER
                    where SQLITE_USER.id eq id
                    ).fetchOne()
}
