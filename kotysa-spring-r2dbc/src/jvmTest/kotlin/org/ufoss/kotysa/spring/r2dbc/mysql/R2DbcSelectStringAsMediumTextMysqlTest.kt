/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MysqlCoroutinesSqlClient
import org.ufoss.kotysa.MysqlReactorSqlClient
import org.ufoss.kotysa.test.MysqlMediumTexts
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.stringAsMediumTextNotNull
import org.ufoss.kotysa.test.stringAsMediumTextNullable


class R2DbcSelectStringAsMediumTextMysqlTest : AbstractR2dbcMysqlTest<UserRepositoryMysqlSelectStringAsMediumText>() {

    override fun instantiateRepository(sqlClient: MysqlReactorSqlClient, coSqlClient: MysqlCoroutinesSqlClient) =
        UserRepositoryMysqlSelectStringAsMediumText(sqlClient)

    @Test
    fun `Verify selectFirstByStringNotNull finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectFirstByStringNotNull(stringAsMediumTextNotNull.stringNotNull).block())
            .isEqualTo(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstByStringNotNull("Unknown").block())
            .isNull()
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsMediumTextNotNull.stringNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull, stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsMediumTextNotNull.stringNotNull, stringAsMediumTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull, stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsMediumTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsMediumTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsMediumTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsMediumTextNotNull.stringNullable).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsMediumTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsMediumTextNotNull.stringNullable).toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsMediumTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsMediumTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsMediumTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsMediumTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsMediumTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toIterable())
            .hasSize(0)
    }
}


class UserRepositoryMysqlSelectStringAsMediumText(private val sqlClient: MysqlReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertMediumTexts().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable MysqlMediumTexts

    private fun insertMediumTexts() = sqlClient.insert(stringAsMediumTextNotNull, stringAsMediumTextNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MysqlMediumTexts

    fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MysqlMediumTexts
                where MysqlMediumTexts.stringNullable endsWith value
                ).fetchAll()
}