/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.MysqlLongTexts
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.stringAsLongTextNotNull
import org.ufoss.kotysa.test.stringAsLongTextNullable

class R2dbcSelectStringAsLongTextMysqlTest : AbstractR2dbcMysqlTest<StringAsLongTextRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = StringAsLongTextRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsLongTextNotNull`() = runTest {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsLongTextNotNull.stringNotNull))
            .isEqualTo(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds no Unknown`() = runTest {
        assertThat(repository.selectFirstOrNullByStringNotNull("Unknown"))
            .isNull()
    }

    @Test
    fun `Verify selectFirstByStringNotNull finds no Unknown, throws NoResultException`() = runTest {
        Assertions.assertThatThrownBy {
            runBlocking {
                repository.selectFirstByStringNotNull("Unknown")
            }
        }.isInstanceOf(NoResultException::class.java)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsLongTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsLongTextNotNull.stringNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() = runTest {
        val seq = sequenceOf(stringAsLongTextNotNull.stringNotNull, stringAsLongTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsLongTextNotNull by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsLongTextNotNull by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsLongTextNotNull by searching bc`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsLongTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(stringAsLongTextNotNull.stringNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsLongTextNullable`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsLongTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsLongTextNotNull.stringNullable).toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsLongTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsLongTextNotNull by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("e").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsLongTextNotNull by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsLongTextNotNull by searching ef`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsLongTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toList())
            .hasSize(0)
    }
}


class StringAsLongTextRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertLongTexts()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable MysqlLongTexts
    }

    private suspend fun insertLongTexts() {
        sqlClient.insert(stringAsLongTextNotNull, stringAsLongTextNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MysqlLongTexts

    suspend fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull eq value
                ).fetchFirstOrNull()

    suspend fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MysqlLongTexts
                where MysqlLongTexts.stringNullable endsWith value
                ).fetchAll()
}
