/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.H2LocalTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.localTimeWithNullable
import org.ufoss.kotysa.test.localTimeWithoutNullable
import java.time.LocalTime


class R2dbcSelectLocalTimeH2Test : AbstractR2dbcH2Test<LocalTimeRepositoryH2Select>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = LocalTimeRepositoryH2Select(sqlClient)

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            localTimeWithNullable.localTimeNotNull,
            localTimeWithoutNullable.localTimeNotNull
        )
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(localTimeWithNullable, localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }
}


class LocalTimeRepositoryH2Select(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertLocalTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable H2LocalTimes
    }

    private suspend fun insertLocalTimes() {
        sqlClient.insert(localTimeWithNullable, localTimeWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2LocalTimes

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNotNull afterOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom H2LocalTimes
                where H2LocalTimes.localTimeNullable afterOrEq localTime
                ).fetchAll()
}
