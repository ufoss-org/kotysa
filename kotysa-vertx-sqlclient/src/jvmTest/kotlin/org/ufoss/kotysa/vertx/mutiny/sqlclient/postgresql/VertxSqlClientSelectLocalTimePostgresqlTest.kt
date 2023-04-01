/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlLocalTimes
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.localTimeWithNullable
import org.ufoss.kotysa.test.localTimeWithoutNullable
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.LocalTime


class VertxSqlClientSelectLocalTimePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<LocalTimeRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = LocalTimeRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
            localTimeWithNullable.localTimeNotNull,
            localTimeWithoutNullable.localTimeNotNull
        )
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(localTimeWithNullable, localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localTimeWithNullable)
    }
}


class LocalTimeRepositoryPostgresqlSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertLocalTimes() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable PostgresqlLocalTimes

    private fun insertLocalTimes() = sqlClient.insert(localTimeWithNullable, localTimeWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlLocalTimes

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNotNull afterOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable eq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable notEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable before localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable beforeOrEq localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable after localTime
                ).fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
        (sqlClient selectFrom PostgresqlLocalTimes
                where PostgresqlLocalTimes.localTimeNullable afterOrEq localTime
                ).fetchAll()
}
