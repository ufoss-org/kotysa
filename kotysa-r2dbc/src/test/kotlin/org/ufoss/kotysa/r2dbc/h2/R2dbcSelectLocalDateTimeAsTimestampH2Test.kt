/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDateTime


class R2dbcSelectLocalDateTimeAsTimestampH2Test : AbstractR2dbcH2Test<LocalDateTimeAsTimestampRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = LocalDateTimeAsTimestampRepositoryH2Select(connection)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeAsTimestampWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime.of(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeAsTimestampWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
                localDateTimeWithNullable.localDateTimeNotNull,
                localDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).toList())
                .hasSize(2)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeAsTimestampWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeAsTimestampWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeAsTimestampWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeAsTimestampWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }
}


class LocalDateTimeAsTimestampRepositoryH2Select(cconnection: Connection) : Repository {

    private val sqlClient = cconnection.sqlClient(h2Tables)

    override fun init() = runBlocking {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable H2_LOCAL_DATE_TIME_AS_TIMESTAMP
    }

    private suspend fun insertLocalDateTimes() {
        sqlClient.insert(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom H2_LOCAL_DATE_TIME_AS_TIMESTAMP
                    where H2_LOCAL_DATE_TIME_AS_TIMESTAMP.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
