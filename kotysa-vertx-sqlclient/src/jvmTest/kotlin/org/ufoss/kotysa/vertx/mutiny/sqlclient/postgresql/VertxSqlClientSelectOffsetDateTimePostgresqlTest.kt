/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlOffsetDateTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.offsetDateTimeWithNullable
import org.ufoss.kotysa.test.offsetDateTimeWithoutNullable
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.OffsetDateTime
import java.time.ZoneOffset


class VertxSqlClientSelectOffsetDateTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<OffsetDateTimeRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = OffsetDateTimeRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNull finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNull(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullNotEq finds offsetDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullNotEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
        )
        assertThat(repository.selectAllByOffsetDateTimeNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBefore finds no results when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullBeforeOrEq finds offsetDateTimeWithNullable when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds offsetDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfter finds no results when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullAfterOrEq finds offsetDateTimeWithoutNullable when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullable(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullable finds offsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds no result`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableNotEq(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableNotEq finds offsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByOffsetDateTimeNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBefore finds no results when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBefore(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableBeforeOrEq finds offsetDateTimeWithNullable when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds offsetDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfter finds no results when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithoutNullable`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNullableAfterOrEq finds offsetDateTimeWithoutNullable when equals`() {
        assertThat(
            repository.selectAllByOffsetDateTimeNullableAfterOrEq(
                OffsetDateTime.of(
                    2018, 11, 4, 0, 0, 0, 0,
                    ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)
                )
            ).await().indefinitely()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(offsetDateTimeWithNullable)
    }
}


class OffsetDateTimeRepositoryPostgresqlSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertOffsetDateTimes() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable PostgresqlOffsetDateTimes

    private fun insertOffsetDateTimes() = sqlClient.insert(offsetDateTimeWithNullable, offsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlOffsetDateTimes

    fun selectAllByOffsetDateTimeNotNull(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullIn(values: Sequence<OffsetDateTime>) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNotNull afterOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullable(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable eq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable notEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBefore(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable before offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable beforeOrEq offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfter(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable after offsetDateTime
                ).fetchAll()

    fun selectAllByOffsetDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) =
        (sqlClient selectFrom PostgresqlOffsetDateTimes
                where PostgresqlOffsetDateTimes.offsetDateTimeNullable afterOrEq offsetDateTime
                ).fetchAll()
}