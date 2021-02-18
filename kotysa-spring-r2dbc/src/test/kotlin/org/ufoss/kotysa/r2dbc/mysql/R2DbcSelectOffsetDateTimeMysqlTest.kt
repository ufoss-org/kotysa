/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.OffsetDateTime
import java.time.ZoneOffset


class R2DbcSelectOffsetDateTimeH2Test : AbstractR2dbcMysqlTest<OffsetDateTimeRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<OffsetDateTimeRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds mysqlOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds mysqlOffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds mysqlOffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds mysqlOffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds mysqlOffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds mysqlOffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlOffsetDateTimeWithNullable)
    }
}


class OffsetDateTimeRepositoryMysqlSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mysqlTables)

    override fun init() {
        createTables()
                .then(insertOffsetDateTimes())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlOffsetDateTime>()

    private fun insertOffsetDateTimes() = sqlClient.insert(mysqlOffsetDateTimeWithNullable, mysqlOffsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlOffsetDateTime>()

    fun selectAllByLocalDateTimeNotNull(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNotNull] afterOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullable(offsetDateTime: OffsetDateTime?) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<MysqlOffsetDateTime>()
            .where { it[MysqlOffsetDateTime::offsetDateTimeNullable] afterOrEq offsetDateTime }
            .fetchAll()
}
*/