/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.test.*

@Order(2)
class R2dbcSelectLongMysqlTest : AbstractR2dbcMysqlTest<LongRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LongRepositoryMysqlSelect(sqlClient)

    private val longWithNullable = LongEntity(
        org.ufoss.kotysa.test.longWithNullable.longNotNull,
        org.ufoss.kotysa.test.longWithNullable.longNullable,
        1
    )

    private val longWithoutNullable = LongEntity(
        org.ufoss.kotysa.test.longWithoutNullable.longNotNull,
        org.ufoss.kotysa.test.longWithoutNullable.longNullable,
        2
    )

    @Test
    fun `Verify selectAllByLongNotNull finds sqLiteIntegerWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNull(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds sqLiteIntegerWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullNotEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() = runTest {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        assertThat(repository.selectAllByLongNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds sqLiteIntegerWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullInf(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullInf(10).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullInfOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullInfOrEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds sqLiteIntegerWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullSup(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullSup(12).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNotNullSupOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNotNullSupOrEq(12).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullable(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableNotEq(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLongNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds sqLiteIntegerWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableInf(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableInf(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableInfOrEq(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableInfOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds sqLiteIntegerWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableSup(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableSup(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLongNullableSupOrEq(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLongNullableSupOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }
}


class LongRepositoryMysqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertLongs()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable MYSQL_LONG
    }

    private suspend fun insertLongs() {
        sqlClient.insert(longWithNullable, longWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MYSQL_LONG

    fun selectAllByLongNotNull(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull eq long
                ).fetchAll()

    fun selectAllByLongNotNullNotEq(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull notEq long
                ).fetchAll()

    fun selectAllByLongNotNullIn(values: Sequence<Long>) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull `in` values
                ).fetchAll()

    fun selectAllByLongNotNullInf(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull inf long
                ).fetchAll()

    fun selectAllByLongNotNullInfOrEq(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull infOrEq long
                ).fetchAll()

    fun selectAllByLongNotNullSup(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull sup long
                ).fetchAll()

    fun selectAllByLongNotNullSupOrEq(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNotNull supOrEq long
                ).fetchAll()

    fun selectAllByLongNullable(long: Long?) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable eq long
                ).fetchAll()

    fun selectAllByLongNullableNotEq(long: Long?) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable notEq long
                ).fetchAll()

    fun selectAllByLongNullableInf(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable inf long
                ).fetchAll()

    fun selectAllByLongNullableInfOrEq(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable infOrEq long
                ).fetchAll()

    fun selectAllByLongNullableSup(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable sup long
                ).fetchAll()

    fun selectAllByLongNullableSupOrEq(long: Long) =
        (sqlClient selectFrom MYSQL_LONG
                where MYSQL_LONG.longNullable supOrEq long
                ).fetchAll()
}
