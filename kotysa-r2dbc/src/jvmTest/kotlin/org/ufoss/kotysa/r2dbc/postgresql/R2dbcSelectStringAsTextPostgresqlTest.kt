/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.NoResultException
import org.ufoss.kotysa.PostgresqlR2dbcSqlClient
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.PostgresqlTexts
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.stringAsTextNotNull
import org.ufoss.kotysa.test.stringAsTextNullable

class R2dbcSelectStringAsTextPostgresqlTest : AbstractR2dbcPostgresqlTest<StringAsTextRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: PostgresqlR2dbcSqlClient) = StringAsTextRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectFirstOrNullByStringNotNull finds stringAsTextNotNull`() = runTest {
        assertThat(repository.selectFirstOrNullByStringNotNull(stringAsTextNotNull.stringNotNull))
            .isEqualTo(stringAsTextNotNull)
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
    fun `Verify selectAllByStringNotNullNotEq ignore stringAsTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq(stringAsTextNotNull.stringNotNull).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullNotEq ignore unknow`() = runTest {
        assertThat(repository.selectAllByStringNotNullNotEq("Unknown").toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullIn finds John and BigBoss`() = runTest {
        val seq = sequenceOf(stringAsTextNotNull.stringNotNull, stringAsTextNullable.stringNotNull)
        assertThat(repository.selectAllByStringNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(stringAsTextNotNull, stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get stringAsTextNotNull by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("b").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullContains get nothing by searching boz`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullContains("boz").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get stringAsTextNotNull by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("ab").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullStartsWith get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullStartsWith("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get stringAsTextNotNull by searching bc`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("bc").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNotNullEndsWith get nothing by searching ab`() = runTest {
        assertThat(repository.selectAllByStringNotNotNullEndsWith("ab").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullable finds stringAsTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(stringAsTextNotNull.stringNullable).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullable with null finds stringAsTextNullable`() = runTest {
        assertThat(repository.selectAllByStringNotNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNullable)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq ignore stringAsTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(stringAsTextNotNull.stringNullable).toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableNotEq with null alias finds stringAsTextNotNull`() = runTest {
        assertThat(repository.selectAllByStringNotNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get stringAsTextNotNull by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("e").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByStringNotNullableContains get nothing by searching b`() = runTest {
        assertThat(repository.selectAllByStringNotNullableContains("b").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByStringNotNullableStartsWith get stringAsTextNotNull by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("de").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasStartsWith get nothing by searching e`() = runTest {
        assertThat(repository.selectAllByStringNotNullableStartsWith("e").toList())
            .hasSize(0)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get stringAsTextNotNull by searching ef`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("ef").toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(stringAsTextNotNull)
    }

    @Test
    fun `Verify selectAllByAliasEndsWith get nothing by searching de`() = runTest {
        assertThat(repository.selectAllByStringNotNullableEndsWith("de").toList())
            .hasSize(0)
    }
}


class StringAsTextRepositoryPostgresqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertTexts()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable PostgresqlTexts
    }

    private suspend fun insertTexts() {
        sqlClient.insert(stringAsTextNotNull, stringAsTextNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom PostgresqlTexts

    suspend fun selectFirstOrNullByStringNotNull(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull eq value
                ).fetchFirstOrNull()

    suspend fun selectFirstByStringNotNull(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull eq value
                ).fetchFirst()

    fun selectAllByStringNotNullNotEq(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull notEq value
                ).fetchAll()

    fun selectAllByStringNotNullIn(values: Sequence<String>) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull `in` values
                ).fetchAll()

    fun selectAllByStringNotNotNullContains(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull contains value
                ).fetchAll()

    fun selectAllByStringNotNotNullStartsWith(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull startsWith value
                ).fetchAll()

    fun selectAllByStringNotNotNullEndsWith(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNotNull endsWith value
                ).fetchAll()

    fun selectAllByStringNotNullable(value: String?) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNullable eq value
                ).fetchAll()

    fun selectAllByStringNotNullableNotEq(value: String?) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNullable notEq value
                ).fetchAll()

    fun selectAllByStringNotNullableContains(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNullable contains value
                ).fetchAll()

    fun selectAllByStringNotNullableStartsWith(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNullable startsWith value
                ).fetchAll()

    fun selectAllByStringNotNullableEndsWith(value: String) =
        (sqlClient selectFrom PostgresqlTexts
                where PostgresqlTexts.stringNullable endsWith value
                ).fetchAll()
}
