/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import kotlinx.datetime.LocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


class SpringJdbcSelectKotlinxLocalDateTimeAsTimestampH2Test : AbstractSpringJdbcH2Test<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>() {
    override val context = startContext<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()

    override val repository = getContextRepository<KotlinxLocalDateTimeAsTimestampRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNull finds h2KotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNull(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullNotEq finds h2KotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullNotEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds h2KotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBefore(LocalDateTime(2019, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds h2KotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq finds h2KotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(LocalDateTime(2019, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds h2KotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfter(LocalDateTime(2019, 11, 6, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds h2KotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq finds h2KotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(LocalDateTime(2019, 11, 6, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds h2KotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBefore(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds h2KotlinxLocalDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 5, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq finds h2KotlinxLocalDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds h2KotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfter(LocalDateTime(2018, 11, 4, 0, 0)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds h2KotlinxLocalDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 3, 12, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeAsTimestampNullableAfterOrEq finds h2KotlinxLocalDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(LocalDateTime(2018, 11, 4, 0, 0)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2KotlinxLocalDateTimeWithNullable)
    }
}


class KotlinxLocalDateTimeAsTimestampRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertLocalDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<H2KotlinxLocalDateTime>()

    private fun insertLocalDateTimes() =
            sqlClient.insert(h2KotlinxLocalDateTimeWithNullable, h2KotlinxLocalDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2KotlinxLocalDateTime>()

    fun selectAllByLocalDateTimeAsTimestampNotNull(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullNotEq(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBefore(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfter(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNotNullAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNotNull] afterOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullable(localDateTime: LocalDateTime?) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] eq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableNotEq(localDateTime: LocalDateTime?) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] notEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBefore(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] before localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableBeforeOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] beforeOrEq localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfter(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] after localDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeAsTimestampNullableAfterOrEq(localDateTime: LocalDateTime) =
            sqlClient.select<H2KotlinxLocalDateTime>()
            .where { it[H2KotlinxLocalDateTime::localDateTimeAsTimestampNullable] afterOrEq localDateTime }
            .fetchAll()
}
