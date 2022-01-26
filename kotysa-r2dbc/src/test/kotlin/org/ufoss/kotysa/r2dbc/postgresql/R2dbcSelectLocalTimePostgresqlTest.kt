/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import io.r2dbc.spi.Connection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalTime

class R2dbcSelectLocalTimePostgresqlTest : AbstractR2dbcPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(connection: Connection) = LocalTimeRepositoryPostgresqlSelect(connection)

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
                localTimeWithoutNullable.localTimeNotNull)
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
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithoutNullable`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullable(null).toList())
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)).toList())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds localTimeWithNullable`() = runTest {
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


class LocalTimeRepositoryPostgresqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() = runBlocking {
        createTables()
        insertLocalTimes()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable POSTGRESQL_LOCAL_TIME
    }

    private suspend fun insertLocalTimes() {
        sqlClient.insert(localTimeWithNullable, localTimeWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_LOCAL_TIME

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull eq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull notEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull before localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull beforeOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull after localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNotNull afterOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable eq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable notEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable before localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable beforeOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable after localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
            (sqlClient selectFrom POSTGRESQL_LOCAL_TIME
                    where POSTGRESQL_LOCAL_TIME.localTimeNullable afterOrEq localTime
                    ).fetchAll()
}
