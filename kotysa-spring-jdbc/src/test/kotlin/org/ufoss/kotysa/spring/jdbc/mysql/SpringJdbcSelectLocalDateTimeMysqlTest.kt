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
import java.time.LocalDateTime


class SpringJdbcSelectLocalDateTimeMysqlTest : AbstractSpringJdbcMysqlTest<LocalDateTimeRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalDateTimeRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                localDateTimeWithNullable.localDateTimeNotNull,
                localDateTimeWithoutNullable.localDateTimeNotNull)
        assertThat(repository.selectAllByLocalDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(localDateTimeWithNullable, localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds localDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(LocalDateTime.of(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(LocalDateTime.of(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds localDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(LocalDateTime.of(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds localDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds localDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(LocalDateTime.of(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localDateTimeWithNullable)
    }
}


class LocalDateTimeRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MYSQL_LOCAL_DATE_TIME
    }

    private fun insertLocalDateTimes() {
        sqlClient.insert(localDateTimeWithNullable, localDateTimeWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MYSQL_LOCAL_DATE_TIME

    fun selectAllByLocalDateTimeNotNull(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullIn(values: Sequence<LocalDateTime>) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNotNull afterOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullable(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable eq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(localDateTime: LocalDateTime?) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable notEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable before localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable beforeOrEq localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable after localDateTime
                    ).fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(localDateTime: LocalDateTime) =
            (sqlClient selectFrom MYSQL_LOCAL_DATE_TIME
                    where MYSQL_LOCAL_DATE_TIME.localDateTimeNullable afterOrEq localDateTime
                    ).fetchAll()
}
