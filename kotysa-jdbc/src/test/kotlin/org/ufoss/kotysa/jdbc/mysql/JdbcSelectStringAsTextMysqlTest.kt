/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectStringAsTextMysqlTest : AbstractJdbcMysqlTest<StringAsTextRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = StringAsTextRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsTextNotNull`() {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsTextNotNull.stringNotNull))
                .isEqualTo(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds no Unknown`() {
        assertThat(repository.selectFirstOrNullByStringNotNull("Unknown"))
                .isNull()
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown, throws NoResultException`() {
        Assertions.assertThatThrownBy { repository.selectFirstByStringNotNull("Unknown") }
                .isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTextNotNull.stringNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsTextNotNull.stringNotNull, stringAsTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsTextNotNull.stringNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTextNotNull.stringNullable))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de"))
                .hasSize(0)
    }
}


class StringAsTextRepositoryMysqlSelect(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertTexts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MYSQL_TEXT
    }

    private fun insertTexts() {
        sqlClient.insert(stringAsTextNotNull, stringAsTextNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_TEXT

    fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom MYSQL_TEXT
                where MYSQL_TEXT.stringNotNull eq value
                ).fetchFirstOrNull()

    fun selectFirstByStringNotNull(value: String) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNotNull eq value
                    ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNotNull notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNotNull `in` values
                    ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MYSQL_TEXT
                where MYSQL_TEXT.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MYSQL_TEXT
                where MYSQL_TEXT.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MYSQL_TEXT
                where MYSQL_TEXT.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNullable eq value
                    ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNullable notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNullable contains value
                    ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNullable startsWith value
                    ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
            (sqlClient selectFrom MYSQL_TEXT
                    where MYSQL_TEXT.stringNullable endsWith value
                    ).fetchAll()
}
