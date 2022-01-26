/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mariadb

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectKotlinxLocalDateTimeMariadbTest : AbstractR2dbcMariadbTest<KotlinxLocalDateTimeRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

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
                kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull)
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
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)).toIterable())
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


class KotlinxLocalDateTimeRepositoryMariadbSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mariadbTables)

    override fun init() {
        createTables()
                .then(insertLocalDateTimes().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable MARIADB_KOTLINX_LOCAL_DATE_TIME

    private fun insertLocalDateTimes() =
            sqlClient.insert(kotlinxLocalDateTimeWithNullable, kotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MARIADB_KOTLINX_LOCAL_DATE_TIME

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MARIADB_KOTLINX_LOCAL_DATE_TIME
                    where MARIADB_KOTLINX_LOCAL_DATE_TIME.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
