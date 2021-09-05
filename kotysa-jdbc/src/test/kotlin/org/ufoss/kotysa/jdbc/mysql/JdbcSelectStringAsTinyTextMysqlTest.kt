/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectStringAsTinyTextMysqlTest : AbstractJdbcMysqlTest<StringAsTinyTextRepositoryMysqlSelect>() {
    override fun instantiateRepository(connection: Connection) = StringAsTinyTextRepositoryMysqlSelect(connection)

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsTinyTextNotNull.stringNotNull))
                .isEqualTo(stringAsTinyTextNotNull)
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
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTinyTextNotNull.stringNotNull))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown"))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() {
        val seq = sequenceOf(stringAsTinyTextNotNull.stringNotNull, stringAsTinyTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTinyTextNotNull by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullContains("b"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() {
        assertThat(repository.selectAllByStringNotNotNullContains("boz"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTinyTextNotNull by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTinyTextNotNull by searching bc`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc"))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab"))
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullable(stringAsTinyTextNotNull.stringNullable))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTinyTextNullable`() {
        assertThat(repository.selectAllByStringNotNullable(null))
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTinyTextNotNull.stringNullable))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTinyTextNotNull`() {
        assertThat(repository.selectAllByStringNotNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTinyTextNotNull by searching e`() {
        assertThat(repository.selectAllByStringNotNullableContains("e"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() {
        assertThat(repository.selectAllByStringNotNullableContains("b"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTinyTextNotNull by searching de`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e"))
                .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTinyTextNotNull by searching ef`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef"))
                .hasSize(1)
                .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de"))
                .hasSize(0)
    }
}


class StringAsTinyTextRepositoryMysqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertTinyTexts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MYSQL_TINY_TEXT
    }

    private fun insertTinyTexts() {
        sqlClient.insert(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_TINY_TEXT

    fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom MYSQL_TINY_TEXT
                where MYSQL_TINY_TEXT.stringNotNull eq value
                ).fetchFirstOrNull()

    fun selectFirstByStringNotNull(value: String) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNotNull eq value
                    ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNotNull notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNotNull `in` values
                    ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MYSQL_TINY_TEXT
                where MYSQL_TINY_TEXT.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MYSQL_TINY_TEXT
                where MYSQL_TINY_TEXT.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MYSQL_TINY_TEXT
                where MYSQL_TINY_TEXT.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNullable eq value
                    ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNullable notEq value
                    ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNullable contains value
                    ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNullable startsWith value
                    ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
            (sqlClient selectFrom MYSQL_TINY_TEXT
                    where MYSQL_TINY_TEXT.stringNullable endsWith value
                    ).fetchAll()
}
