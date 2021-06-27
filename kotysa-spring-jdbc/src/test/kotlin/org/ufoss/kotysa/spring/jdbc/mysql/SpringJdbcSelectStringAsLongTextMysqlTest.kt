/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectStringAsLongTextMysqlTest : AbstractSpringJdbcMysqlTest<StringAsLongTextRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<StringAsLongTextRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsLongTextNotNull`() {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsLongTextNotNull.stringNotNull))
                .isEqualTo(stringAsLongTextNotNull)
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
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsLongTextNotNull.stringNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsLongTextNotNull.stringNotNull, stringAsLongTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsLongTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsLongTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsLongTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsLongTextNotNull.stringNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsLongTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsLongTextNotNull.stringNullable))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsLongTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsLongTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsLongTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsLongTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de"))
                .hasSize(0)
    }
}


class StringAsLongTextRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertLongTexts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MYSQL_LONG_TEXT
    }

    private fun insertLongTexts() {
        sqlClient.insert(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_LONG_TEXT

    fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom MYSQL_LONG_TEXT
                where MYSQL_LONG_TEXT.stringNotNull eq value
                ).fetchFirstOrNull()

    fun selectFirstByStringNotNull(value: String) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNotNull eq value
                    ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNotNull notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNotNull `in` values
                    ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MYSQL_LONG_TEXT
                where MYSQL_LONG_TEXT.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MYSQL_LONG_TEXT
                where MYSQL_LONG_TEXT.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MYSQL_LONG_TEXT
                where MYSQL_LONG_TEXT.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNullable eq value
                    ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNullable notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNullable contains value
                    ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNullable startsWith value
                    ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
            (sqlClient selectFrom MYSQL_LONG_TEXT
                    where MYSQL_LONG_TEXT.stringNullable endsWith value
                    ).fetchAll()
}
