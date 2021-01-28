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
import java.time.LocalTime


class SpringJdbcSelectLocalTimeH2Test : AbstractSpringJdbcH2Test<LocalTimeRepositoryH2Select>() {
    override val context = startContext<LocalTimeRepositoryH2Select>()

    override val repository = getContextRepository<LocalTimeRepositoryH2Select>()

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds h2LocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds h2LocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                h2LocalTimeWithNullable.localTimeNotNull,
                h2LocalTimeWithoutNullable.localTimeNotNull)
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable, h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds h2LocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds h2LocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds h2LocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds h2LocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds h2LocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds h2LocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds h2LocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds h2LocalTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds h2LocalTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds h2LocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds h2LocalTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds h2LocalTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2LocalTimeWithNullable)
    }
}


class LocalTimeRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertLocalTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<H2LocalTime>()

    private fun insertLocalTimes() = sqlClient.insert(h2LocalTimeWithNullable, h2LocalTimeWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2LocalTime>()

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
            sqlClient.select<H2LocalTime>()
                    .where { it[H2LocalTime::localTimeNotNull] `in` values }
                    .fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNotNull] afterOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] eq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] notEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] before localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] beforeOrEq localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] after localTime }
            .fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) = sqlClient.select<H2LocalTime>()
            .where { it[H2LocalTime::localTimeNullable] afterOrEq localTime }
            .fetchAll()
}
*/