/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectStringAsLongTextMariadbTest : AbstractR2dbcMariadbTest<UserRepositoryMariadbSelectStringAsLongText>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<UserRepositoryMariadbSelectStringAsLongText>(resource)
    }

    override val repository: UserRepositoryMariadbSelectStringAsLongText by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds stringAsLongTextNotNull`() {
        assertThat(repository.selectFirstByStringNotNull(stringAsLongTextNotNull.stringNotNull).block())
            .isEqualTo(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstByStringNotNull("Unknown").block())
            .isNull()
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsLongTextNotNull.stringNotNull).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsLongTextNotNull.stringNotNull, stringAsLongTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsLongTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsLongTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsLongTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsLongTextNotNull.stringNullable).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsLongTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsLongTextNotNull.stringNullable).toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsLongTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsLongTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toIterable())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsLongTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toIterable())
            .hasSize(0)
    }
}


class UserRepositoryMariadbSelectStringAsLongText(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mariadbTables)

    override fun init() {
        createTables()
            .then(insertTexts().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable MariadbLongTexts

    private fun insertTexts() = sqlClient.insert(stringAsLongTextNotNull, stringAsLongTextNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbLongTexts

    fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MariadbLongTexts
                where MariadbLongTexts.stringNullable endsWith value
                ).fetchAll()
}
