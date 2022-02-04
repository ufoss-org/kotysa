/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSelectKotlinxLocalDateMysqlTest : AbstractJdbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = KotlinxLocalDateRepositoryMysqlSelect(sqlClient)

    @Test
    fun `Verify selectAllByLocalDateNotNull finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullIn finds both`() {
        val seq = sequenceOf(
                kotlinxLocalDateWithNullable.localDateNotNull,
                kotlinxLocalDateWithoutNullable.localDateNotNull)
        assertThat(repository.selectAllByLocalDateNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds kotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds kotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(kotlinxLocalDateWithNullable)
    }
}


class KotlinxLocalDateRepositoryMysqlSelect(private val sqlClient: JdbcSqlClient) : Repository {

    override fun init() {
        createTables()
        insertLocalDates()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MysqlKotlinxLocalDates
    }

    private fun insertLocalDates() {
        sqlClient.insert(kotlinxLocalDateWithNullable, kotlinxLocalDateWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MysqlKotlinxLocalDates

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullIn(values: Sequence<LocalDate>) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNotNull afterOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable eq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable notEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable before localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable beforeOrEq localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable after localDate
                    ).fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            (sqlClient selectFrom MysqlKotlinxLocalDates
                    where MysqlKotlinxLocalDates.localDateNullable afterOrEq localDate
                    ).fetchAll()
}
