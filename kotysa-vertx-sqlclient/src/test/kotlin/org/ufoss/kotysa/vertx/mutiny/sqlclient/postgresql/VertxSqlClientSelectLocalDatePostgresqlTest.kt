/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.PostgresqlLocalDates
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.localDateWithNullable
import org.ufoss.kotysa.test.localDateWithoutNullable
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import java.time.LocalDate


class VertxSqlClientSelectLocalDatePostgresqlTest :
    AbstractVertxSqlClientPostgresqlTest<LocalDateRepositoryPostgresqlSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = LocalDateRepositoryPostgresqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
            localDateWithNullable.localDateNotNull,
            localDateWithoutNullable.localDateNotNull
        )
        assertThat(repository.selectAllByLocalDateNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(localDateWithNullable, localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds localDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds localDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds localDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds localDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(localDateWithNullable)
    }
}


class LocalDateRepositoryPostgresqlSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertLocalDates() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable PostgresqlLocalDates

    private fun insertLocalDates() = sqlClient.insert(localDateWithNullable, localDateWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom PostgresqlLocalDates

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull `in` values
                ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull before localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull after localDate
                ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNotNull afterOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable eq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable notEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable before localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable beforeOrEq localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable after localDate
                ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
        (sqlClient selectFrom PostgresqlLocalDates
                where PostgresqlLocalDates.localDateNullable afterOrEq localDate
                ).fetchAll()
}
