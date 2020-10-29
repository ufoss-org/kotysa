/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.LocalDate


class SpringJdbcSelectLocalDateMysqlTest : AbstractSpringJdbcMysqlTest<LocalDateRepositoryMysqlSelect>() {
    override val context = startContext<LocalDateRepositoryMysqlSelect>()

    override val repository = getContextRepository<LocalDateRepositoryMysqlSelect>()

    @Test
    fun `Verify selectAllByLocalDateNotNull finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNull(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullNotEq finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullNotEq(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBefore(LocalDate.of(2019, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullBeforeOrEq finds mysqlLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullBeforeOrEq(LocalDate.of(2019, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfter(LocalDate.of(2019, 11, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNotNullAfterOrEq finds mysqlLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNotNullAfterOrEq(LocalDate.of(2019, 11, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullable finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(LocalDate.of(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableNotEq finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBefore(LocalDate.of(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds mysqlLocalDateWithNullable`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableBeforeOrEq finds mysqlLocalDateWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableBeforeOrEq(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfter(LocalDate.of(2018, 11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds mysqlLocalDateWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateNullableAfterOrEq finds mysqlLocalDateWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateNullableAfterOrEq(LocalDate.of(2018, 11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalDateWithNullable)
    }
}


class LocalDateRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertLocalDates()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlLocalDate>()

    private fun insertLocalDates() = sqlClient.insert(mysqlLocalDateWithNullable, mysqlLocalDateWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlLocalDate>()

    fun selectAllByLocalDateNotNull(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullNotEq(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBefore(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullBeforeOrEq(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfter(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNotNullAfterOrEq(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNotNull] afterOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullable(localDate: LocalDate?) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] eq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableNotEq(localDate: LocalDate?) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] notEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBefore(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] before localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableBeforeOrEq(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] beforeOrEq localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfter(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] after localDate }
            .fetchAll()

    fun selectAllByLocalDateNullableAfterOrEq(localDate: LocalDate) = sqlClient.select<MysqlLocalDate>()
            .where { it[MysqlLocalDate::localDateNullable] afterOrEq localDate }
            .fetchAll()
}
