/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.LocalTime


class SpringJdbcSelectLocalTimeMysqlTest : AbstractSpringJdbcMysqlTest<LocalTimeRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalTimeRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds mysqlLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds mysqlLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds mysqlLocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds mysqlLocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds mysqlLocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds mysqlLocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlLocalTimeWithNullable)
    }
}


class LocalTimeRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertLocalTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlLocalTime>()

    private fun insertLocalTimes() = sqlClient.insert(mysqlLocalTimeWithNullable, mysqlLocalTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlLocalTime>()

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNotNull] afterOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) = sqlClient.select<MysqlLocalTime>()
            .where { it[MysqlLocalTime::localTimeNullable] afterOrEq localTime }
            .fetchAll()
}
