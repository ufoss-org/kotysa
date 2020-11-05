/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import kotlinx.datetime.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectKotlinxLocalDateMysqlTest : AbstractR2dbcMysqlTest<KotlinxLocalDateRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNull finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate(2019, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds mysqlKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate(2019, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate(2019, 11, 6)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds mysqlKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate(2019, 11, 6)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds mysqlKotlinxLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 5)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds mysqlKotlinxLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate(2018, 11, 4)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds mysqlKotlinxLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 3)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds mysqlKotlinxLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate(2018, 11, 4)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateWithNullable)
    }
}


class KotlinxLocalDateRepositoryMysqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mysqlTables)

    override fun init() {
        createTables()
                .then(insertLocalDates())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlKotlinxLocalDate>()

    private fun insertLocalDates() =
            sqlClient.insert(mysqlKotlinxLocalDateWithNullable, mysqlKotlinxLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlKotlinxLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNotNull] afterOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] eq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] notEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] before localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] beforeOrEq localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] after localDate }
                    .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) =
            sqlClient.select<MysqlKotlinxLocalDate>()
                    .where { it[MysqlKotlinxLocalDate::localDateNullable] afterOrEq localDate }
                    .fetchAll()
}
