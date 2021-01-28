/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2
/*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.time.OffsetDateTime
import java.time.ZoneOffset


class SpringJdbcSelectOffsetDateTimeH2Test : AbstractSpringJdbcH2Test<OffsetDateTimeRepositoryH2Select>() {
    override val context = startContext<OffsetDateTimeRepositoryH2Select>()

    override val repository = getContextRepository<OffsetDateTimeRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalDateTimeNotNull finds h2OffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNull(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullNotEq finds h2OffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullNotEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByOffsetDateTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC),
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)
        )
        assertThat(repository.selectAllByOffsetDateTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable, h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds h2OffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBefore(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds h2OffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullBeforeOrEq finds h2OffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullBeforeOrEq(
                OffsetDateTime.of(2019, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds h2OffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfter(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds h2OffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNotNullAfterOrEq finds h2OffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNotNullAfterOrEq(
                OffsetDateTime.of(2019, 11, 6, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalDateTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds h2OffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBefore(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds h2OffsetDateTimeWithNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 5, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableBeforeOrEq finds h2OffsetDateTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableBeforeOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds h2OffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfter(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds h2OffsetDateTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 3, 12, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalDateTimeNullableAfterOrEq finds h2OffsetDateTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalDateTimeNullableAfterOrEq(
                OffsetDateTime.of(2018, 11, 4, 0, 0, 0, 0, ZoneOffset.UTC)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2OffsetDateTimeWithNullable)
    }
}


class OffsetDateTimeRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertOffsetDateTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<H2OffsetDateTime>()

    private fun insertOffsetDateTimes() = sqlClient.insert(h2OffsetDateTimeWithNullable, h2OffsetDateTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2OffsetDateTime>()

    fun selectAllByLocalDateTimeNotNull(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullNotEq(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByOffsetDateTimeNotNullIn(values: Sequence<OffsetDateTime>) =
            sqlClient.select<H2OffsetDateTime>()
                    .where { it[H2OffsetDateTime::offsetDateTimeNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalDateTimeNotNullBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNotNullAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNotNull] afterOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullable(offsetDateTime: OffsetDateTime?) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] eq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableNotEq(offsetDateTime: OffsetDateTime?) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] notEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBefore(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] before offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableBeforeOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] beforeOrEq offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfter(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] after offsetDateTime }
            .fetchAll()

    fun selectAllByLocalDateTimeNullableAfterOrEq(offsetDateTime: OffsetDateTime) = sqlClient.select<H2OffsetDateTime>()
            .where { it[H2OffsetDateTime::offsetDateTimeNullable] afterOrEq offsetDateTime }
            .fetchAll()
}
*/