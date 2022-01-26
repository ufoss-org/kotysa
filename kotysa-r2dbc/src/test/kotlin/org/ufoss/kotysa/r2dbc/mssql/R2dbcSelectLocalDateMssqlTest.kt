/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate

class R2dbcSelectLocalDateMssqlTest : AbstractR2dbcMssqlTest<LocalDateRepositoryMssqlSelect>() {
    override fun instantiateRepository(connection: Connection) = LocalDateRepositoryMssqlSelect(connection)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
                localDateWithNullable.localDateNotNull,
                localDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateWithNullable)
    }
}


class LocalDateRepositoryMssqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(mssqlTables)

    override fun init() = runBlocking {
        createTables()
        insertLocalDates()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable MSSQL_LOCAL_DATE
    }

    private suspend fun insertLocalDates() {
        sqlClient.insert(localDateWithNullable, localDateWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom MSSQL_LOCAL_DATE

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNotNull afterOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MSSQL_LOCAL_DATE
                    where MSSQL_LOCAL_DATE.localDateNullable afterOrEq localDate
                    ).fetchAll()
}
