/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.SqLiteUser
import org.ufoss.kotysa.test.sqLiteBboss
import org.ufoss.kotysa.test.sqLiteJdoe

class SqLiteSelectStringTest : AbstractSqLiteTest<UserRepositoryStringSelect>() {

    override fun getRepository(sqLiteTables: Tables) = UserRepositoryStringSelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectFirstByFirstname finds John`() {
        assertThat(repository.selectFirstByFirstname("John"))
            .isEqualTo(sqLiteJdoe)
    }

    @Test
    fun `Verify selectFirstByFirstname finds no Unknown`() {
        assertThat(repository.selectFirstByFirstname("Unknown"))
            .isNull()
    }

    @Test
    fun `Verify selectFirstByFirstnameNotNullable finds no Unknown, throws NoResultException`() {
        assertThatThrownBy { repository.selectFirstByFirstnameNotNullable("Unknown") }
            .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectByAlias finds TheBoss`() {
        assertThat(repository.selectByAlias("TheBoss").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectByAlias with null alias finds John`() {
        assertThat(repository.selectByAlias(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore John`() {
        assertThat(repository.selectAllByFirstnameNotEq(sqLiteJdoe.firstname))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameNotEq ignore unknow`() {
        assertThat(repository.selectAllByFirstnameNotEq("Unknown"))
            .hasSize(2)
            .containsExactlyInAnyOrder(sqLiteJdoe, sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByAliasNotEq ignore BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(sqLiteBboss.alias))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasNotEq with null alias finds BigBoss`() {
        assertThat(repository.selectAllByAliasNotEq(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get John by searching oh`() {
        assertThat(repository.selectAllByFirstnameContains("oh"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameContains get nothing by searching boz`() {
        assertThat(repository.selectAllByFirstnameContains("boz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get John by searching Joh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("Joh"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameStartsWith get nothing by searching oh`() {
        assertThat(repository.selectAllByFirstnameStartsWith("oh"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get John by searching ohn`() {
        assertThat(repository.selectAllByFirstnameEndsWith("ohn"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteJdoe)
    }

    @Test
    fun `Verify selectAllByFirstnameEndsWith get nothing by searching joh`() {
        assertThat(repository.selectAllByFirstnameEndsWith("joh"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasContains get Boss by searching heBos`() {
        assertThat(repository.selectAllByAliasContains("heBos"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByAliasContains get nothing by searching heBoz`() {
        assertThat(repository.selectAllByAliasContains("heBoz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get Boss by searching TheBo`() {
        assertThat(repository.selectAllByAliasStartsWith("TheBo"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching heBo`() {
        assertThat(repository.selectAllByAliasStartsWith("heBo"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get Boss by searching Boss`() {
        assertThat(repository.selectAllByAliasEndsWith("Boss"))
            .hasSize(1)
            .containsExactlyInAnyOrder(sqLiteBboss)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching TheBo`() {
        assertThat(repository.selectAllByAliasEndsWith("TheBo"))
            .hasSize(0)
    }
}

class UserRepositoryStringSelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: Tables
) : AbstractUserRepository(sqLiteOpenHelper, tables) {

    fun selectFirstByFirstnameNotNullable(firstname: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] eq firstname }
            .fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] notEq firstname }
            .fetchAll()

    fun selectByAlias(alias: String?) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::alias] eq alias }
            .fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::alias] notEq alias }
            .fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] contains firstnameContains }
            .fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] startsWith firstnameStartsWith }
            .fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::firstname] endsWith firstnameEndsWith }
            .fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::alias] contains aliasContains }
            .fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::alias] startsWith aliasStartsWith }
            .fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
        sqlClient.select<SqLiteUser>()
            .where { it[SqLiteUser::alias] endsWith aliasEndsWith }
            .fetchAll()
}
