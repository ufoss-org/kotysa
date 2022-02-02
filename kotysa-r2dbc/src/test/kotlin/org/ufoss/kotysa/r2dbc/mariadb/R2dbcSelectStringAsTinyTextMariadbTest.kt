/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.MARIADB_TINY_TEXT
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.stringAsTinyTextNotNull
import org.ufoss.kotysa.test.stringAsTinyTextNullable

class R2dbcSelectStringAsTinyTextMariadbTest : AbstractR2dbcMariadbTest<StringAsTinyTextRepositoryMariadbSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = StringAsTinyTextRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsTinyTextNotNull`() = runTest {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsTinyTextNotNull.stringNotNull))
            .isEqualTo(stringAsTinyTextNotNull)
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
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTinyTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTinyTextNotNull.stringNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() = runTest {
        val seq = sequenceOf(stringAsTinyTextNotNull.stringNotNull, stringAsTinyTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTinyTextNotNull by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTinyTextNotNull by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTinyTextNotNull by searching bc`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTinyTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(stringAsTinyTextNotNull.stringNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTinyTextNullable`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTinyTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTinyTextNotNull.stringNullable).toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTinyTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTinyTextNotNull by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("e").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTinyTextNotNull by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTinyTextNotNull by searching ef`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTinyTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toList())
            .hasSize(0)
    }
}


class StringAsTinyTextRepositoryMariadbSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertTexts()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable MARIADB_TINY_TEXT
    }

    private suspend fun insertTexts() {
        sqlClient.insert(stringAsTinyTextNotNull, stringAsTinyTextNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MARIADB_TINY_TEXT

    suspend fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull eq value
                ).fetchFirstOrNull()

    suspend fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom MARIADB_TINY_TEXT
                where MARIADB_TINY_TEXT.stringNullable endsWith value
                ).fetchAll()
}
