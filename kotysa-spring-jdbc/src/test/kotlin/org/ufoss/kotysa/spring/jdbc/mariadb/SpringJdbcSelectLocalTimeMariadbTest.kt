/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource
import java.time.LocalTime


class SpringJdbcSelectLocalTimeMariadbTest : AbstractSpringJdbcMariadbTest<LocalTimeRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LocalTimeRepositoryMariadbSelect>(resource)
    }

    override val repository: LocalTimeRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNull finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNull(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullNotEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullNotEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullIn finds both`() {
        val seq = sequenceOf(
                localTimeWithNullable.localTimeNotNull,
                localTimeWithoutNullable.localTimeNotNull)
        assertThat(repository.selectAllByLocalTimeNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(localTimeWithNullable, localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBefore(LocalTime.of(12, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullBeforeOrEq finds localTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullBeforeOrEq(LocalTime.of(12, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfter(LocalTime.of(12, 6)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNotNullAfterOrEq finds localTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNotNullAfterOrEq(LocalTime.of(12, 6)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullable finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableNotEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBefore finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBefore(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 5)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableBeforeOrEq finds localTimeWithNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableBeforeOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfter finds no results when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfter(LocalTime.of(11, 4)))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 3)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }

    @Test
    fun `Verify selectAllByLocalTimeNullableAfterOrEq finds localTimeWithoutNullable when equals`() {
        assertThat(repository.selectAllByLocalTimeNullableAfterOrEq(LocalTime.of(11, 4)))
                .hasSize(1)
                .containsExactlyInAnyOrder(localTimeWithNullable)
    }
}


class LocalTimeRepositoryMariadbSelect(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(mariadbTables)

    override fun init() {
        createTables()
        insertLocalTimes()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MARIADB_LOCAL_TIME
    }

    private fun insertLocalTimes() {
        sqlClient.insert(localTimeWithNullable, localTimeWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MARIADB_LOCAL_TIME

    fun selectAllByLocalTimeNotNull(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull eq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullNotEq(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull notEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullIn(values: Sequence<LocalTime>) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull `in` values
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullBefore(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull before localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullBeforeOrEq(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull beforeOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullAfter(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull after localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNotNullAfterOrEq(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNotNull afterOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullable(localTime: LocalTime?) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable eq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableNotEq(localTime: LocalTime?) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable notEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableBefore(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable before localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableBeforeOrEq(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable beforeOrEq localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableAfter(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable after localTime
                    ).fetchAll()

    fun selectAllByLocalTimeNullableAfterOrEq(localTime: LocalTime) =
            (sqlClient selectFrom MARIADB_LOCAL_TIME
                    where MARIADB_LOCAL_TIME.localTimeNullable afterOrEq localTime
                    ).fetchAll()
}
