/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql
/*
import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class SpringJdbcSelectKotlinxLocalDateTimeMysqlTest : AbstractSpringJdbcMysqlTest<KotlinxLocalDateTimeRepositoryMysqlSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<KotlinxLocalDateTimeRepositoryMysqlSelect>(resource)
        repository = getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNull finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullNotEq finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds mysqlKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds mysqlKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds mysqlKotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds mysqlKotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds mysqlKotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds mysqlKotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(mysqlKotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeRepositoryMysqlSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mysqlTables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<MysqlKotlinxLocalDateTime>()

    private fun insertLocalDateTimes() =
            sqlClient.insert(mysqlKotlinxLocalDateTimeWithNullable, mysqlKotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<MysqlKotlinxLocalDateTime>()

    fun selectAllByLocalDateTimeAsTimestampNotNull(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] eq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullNotEq(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] notEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBefore(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] before localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] beforeOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfter(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] after localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNotNull] afterOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullable(localDateTime: LocalDateTime?) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] eq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableNotEq(localDateTime: LocalDateTime?) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] notEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBefore(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] before localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] beforeOrEq localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfter(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] after localDateTime }
                    .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<MysqlKotlinxLocalDateTime>()
                    .where { it[MysqlKotlinxLocalDateTime::localDateTimeNullable] afterOrEq localDateTime }
                    .fetchAll()
}
*/