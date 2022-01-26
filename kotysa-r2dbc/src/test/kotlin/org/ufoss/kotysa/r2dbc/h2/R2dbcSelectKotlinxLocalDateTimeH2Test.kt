/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*


class R2dbcSelectKotlinxLocalDateTimeH2Test : AbstractR2dbcH2Test<KotlinxLocalDateTimeRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = KotlinxLocalDateTimeRepositoryH2Select(connection)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
                kotlinxLocalDateTimeWithNullable.localDateTimeNotNull,
                kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryH2Select(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(h2Tables)

    override fun init() = runBlocking {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable H2_KOTLINX_LOCAL_DATE_TIME
    }

    private suspend fun insertLocalDateTimes() {
        sqlClient.insert(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2_KOTLINX_LOCAL_DATE_TIME

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_KOTLINX_LOCAL_DATE_TIME
                    where H2_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
