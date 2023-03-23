/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.MariadbCoroutinesSqlClient
import org.ufoss.kotysa.MariadbReactorSqlClient
import org.ufoss.kotysa.test.MariadbKotlinxLocalDateTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.kotlinxLocalDateTimeWithNullable
import org.ufoss.kotysa.test.kotlinxLocalDateTimeWithoutNullable


class R2DbcSelectKotlinxLocalDateTimeMariadbTest :
    AbstractR2dbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: MariadbReactorSqlClient, coSqlClient: MariadbCoroutinesSqlClient) =
        KotlinxLocalDateTimeRepositoryMariadbSelect(sqlClient)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            kotlinxLocalDateTimeWithNullable.localDateTimeNotNull,
            kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull
        )
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq).toIterable())
            .hasSize(2)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable`() {
        assertThat(
            repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)).toIterable()
        )
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)).toIterable())
            .hasSize(1)
            .containsExactlyInAnyOrder(kotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryMariadbSelect(private val sqlClient: MariadbReactorSqlClient) : Repository {

    override fun init() {
        createTables()
            .then(insertLocalDateTimes().then())
            .block()
    }

    override fun delete() {
        deleteAll()
            .block()
    }

    private fun createTables() = sqlClient createTable MariadbKotlinxLocalDateTimes

    private fun insertLocalDateTimes() =
        sqlClient.insert(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbKotlinxLocalDateTimes

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull eq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull notEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull before localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull beforeOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull after localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNotNull afterOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable eq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable notEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable before localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable beforeOrEq localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable after localDateTime
                ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
        (sqlClient selectFrom MariadbKotlinxLocalDateTimes
                where MariadbKotlinxLocalDateTimes.localDateTimeNullable afterOrEq localDateTime
                ).fetchAll()
}
