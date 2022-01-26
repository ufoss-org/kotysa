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
import java.time.OffsetDateTime
import java.time.ZoneOffset

class R2dbcSelectOffsetDateTimeH2Test : AbstractR2dbcH2Test<OffsetDateTimeRepositoryH2Select>() {
    override fun instantiateRepository(connection: Connection) = OffsetDateTimeRepositoryH2Select(connection)

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNull(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullNotEq finds offsetDateTimeWithoutNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullNotEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullIn finds both`() = runTest {
        val seq = sequenceOf(
            OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
        )
        assertThat(repository.selectAllByOffsetDateTimeNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds offsetDateTimeWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds offsetDateTimeWithoutNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds h2UuidWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullable(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds h2UuidWithoutNullable`() = runTest {
        assertThat(repository.selectAllByOffsetDateTimeNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no result`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableNotEq(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).toList()
        ).isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds offsetDateTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByOffsetDateTimeNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds offsetDateTimeWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBefore(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).toList()
        ).isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds offsetDateTimeWithoutNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithoutNullable`() = runTest {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)
            ).toList()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithoutNullable when equals`() =
        runTest {
            assertThat(
                repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                    OffsetDateTime.of(
                        2018, 11, 4, 0, 0, 0, 0,
                        ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                    )
                ).toList()
            ).hasSize(1)
                .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
        }
}


class OffsetDateTimeRepositoryH2Select(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(h2Tables)

    override fun init() = runBlocking {
        createTables()
        insertOffsetDateTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable H2_OFFSET_DATE_TIME
    }

    private suspend fun insertOffsetDateTimes() {
        sqlClient.insert(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom H2_OFFSET_DATE_TIME

    fun selectAllByOffsetDateTimeNotNull(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullIn(values: Sequence<OffsetDateTime>) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNotNull afterOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullable(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom H2_OFFSET_DATE_TIME
                where H2_OFFSET_DATE_TIME.offsetDateTimeNullable afterOrEq offsetDateTime
                ).fetchAll()
}
