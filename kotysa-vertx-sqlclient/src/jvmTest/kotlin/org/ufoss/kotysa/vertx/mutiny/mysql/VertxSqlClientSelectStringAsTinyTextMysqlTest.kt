/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectStringAsTinyTextMysqlTest : AbstractVertxSqlClientMysqlTest<StringAsTinyTextRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = StringAsTinyTextRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectFirstByStringNotNull finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectFirstByStringNotNull(stringAsTinyTextNotNull.stringNotNull).await().indefinitely())
            .isEqualTo(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstByStringNotNull("Unknown").await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTinyTextNotNull.stringNotNull).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsTinyTextNotNull.stringNotNull, stringAsTinyTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTinyTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTinyTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTinyTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsTinyTextNotNull.stringNullable).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTinyTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTinyTextNotNull.stringNullable).await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTinyTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTinyTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTinyTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").await().indefinitely())
            .hasSize(0)
    }
}


class StringAsTinyTextRepositoryMysqlSelect(private val sqlClient: MutinyVertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertTinyTexts() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTableIfNotExists MysqlTinyTexts

    private fun insertTinyTexts() = sqlClient.insert(stringAsTinyTextNotNull, stringAsTinyTextNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MysqlTinyTexts

    fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MysqlTinyTexts
                where MysqlTinyTexts.stringNullable endsWith value
                ).fetchAll()
}
