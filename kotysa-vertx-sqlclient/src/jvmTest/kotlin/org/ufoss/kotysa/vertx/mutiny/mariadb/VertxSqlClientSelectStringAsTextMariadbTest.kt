/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient

class VertxSqlClientSelectStringAsTextMariadbTest :
    AbstractVertxSqlClientMariadbTest<UserRepositoryMariadbSelectStringAsText>() {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        UserRepositoryMariadbSelectStringAsText(sqlClient)

    @Test
    fun `Verify selectFirstByStringNotNull finds stringAsTextNotNull`() {
        assertThat(repository.selectFirstByStringNotNull(stringAsTextNotNull.stringNotNull).await().indefinitely())
            .isEqualTo(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstByStringNotNull("Unknown").await().indefinitely())
            .isNull()
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTextNotNull.stringNotNull).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsTextNotNull.stringNotNull, stringAsTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsTextNotNull.stringNullable).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTextNotNull`() {
        assertThat(
            repository.selectAllByStringNotNullableNotEq(stringAsTextNotNull.stringNullable).await().indefinitely()
        )
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").await().indefinitely())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").await().indefinitely())
            .hasSize(0)
    }
}


class UserRepositoryMariadbSelectStringAsText(private val sqlClient: MutinyVertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertTexts() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTableIfNotExists MariadbTexts

    private fun insertTexts() = sqlClient.insert(stringAsTextNotNull, stringAsTextNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbTexts

    fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MariadbTexts
                where MariadbTexts.stringNullable endsWith value
                ).fetchAll()
}
