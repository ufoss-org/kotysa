/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection
import java.time.LocalDateTime


class JdbcSelectLocalDateTimePostgresqlTest : AbstractJdbcPostgresqlTest<LocalDateTimeRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(connection: Connection) = LocalDateTimeRepositoryPostgresqlSelect(connection)

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                localDateTimeWithNullable.localDateTimeNotNull,
                localDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeAsTimestampWithNullable)
    }
}


class LocalDateTimeRepositoryPostgresqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable POSTGRESQL_LOCAL_DATE_TIME
    }

    private fun insertLocalDateTimes() {
        sqlClient.insert(localDateTimeAsTimestampWithNullable, localDateTimeAsTimestampWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_LOCAL_DATE_TIME

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_DATE_TIME
                    where POSTGRESQL_LOCAL_DATE_TIME.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
