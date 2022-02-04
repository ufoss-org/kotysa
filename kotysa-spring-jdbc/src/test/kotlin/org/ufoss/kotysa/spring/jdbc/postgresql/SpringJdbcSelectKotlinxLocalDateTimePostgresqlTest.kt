/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.postgresql

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectKotlinxLocalDateTimePostgresqlTest : AbstractSpringJdbcPostgresqlTest<KotlinxLocalDateTimeRepositoryPostgresqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryPostgresqlSelect>(resource)
    }

    override val repository: KotlinxLocalDateTimeRepositoryPostgresqlSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                kotlinxLocalDateTimeWithNullable.localDateTimeNotNull,
                kotlinxLocalDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable, kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds kotlinxLocalDateTimeAsTimestampWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds kotlinxLocalDateTimeAsTimestampWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateTimeAsTimestampWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryPostgresqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable PostgresqlKotlinxLocalDateTimeAsTimestamps
    }

    private fun insertLocalDateTimes() {
        sqlClient.insert(kotlinxLocalDateTimeAsTimestampWithNullable, kotlinxLocalDateTimeAsTimestampWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlKotlinxLocalDateTimeAsTimestamps

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom PostgresqlKotlinxLocalDateTimeAsTimestamps
                    where PostgresqlKotlinxLocalDateTimeAsTimestamps.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
