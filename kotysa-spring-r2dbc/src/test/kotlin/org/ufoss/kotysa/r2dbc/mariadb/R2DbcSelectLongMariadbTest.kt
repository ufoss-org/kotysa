/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.hooks.TestContainersCloseableResource


class R2DbcSelectLongMariadbTest : AbstractR2dbcMariadbTest<LongRepositoryMariadbSelect>() {

    @BeforeAll
    fun beforeAll(resource: TestContainersCloseableResource) {
        context = startContext<LongRepositoryMariadbSelect>(resource)
    }

    override val repository: LongRepositoryMariadbSelect by lazy {
        getContextRepository()
    }

    private val longWithNullable = LongEntity(
            org.ufoss.kotysa.test.longWithNullable.longNotNull,
            org.ufoss.kotysa.test.longWithNullable.longNullable,
            1
    )

    private val longWithoutNullable = LongEntity(
            org.ufoss.kotysa.test.longWithoutNullable.longNotNull,
            org.ufoss.kotysa.test.longWithoutNullable.longNullable,
            2
    )

    @Test
    fun `Verify selectAllByLongNotNull finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNull(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullNotEq(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        assertThat(repository.selectAllByLongNotNullIn(seq).toIterable())
                .hasSize(2)
                .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInf(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullInf(10).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(10).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSup(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullSup(12).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(11).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(12).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLongNullable(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullable(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableNotEq(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLongNullableNotEq(null).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInf(7).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableInf(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(7).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSup(5).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableSup(6).toIterable())
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(5).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(6).toIterable())
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }
}


class LongRepositoryMariadbSelect(dbClient: DatabaseClient) : Repository {

    private val sqlClient = dbClient.sqlClient(mariadbTables)

    override fun init() {
        createTables()
                .then(insertLongs().then())
                .block()
    }

    override fun delete() {
        deleteAll()
                .block()
    }

    private fun createTables() = sqlClient createTable MARIADB_LONG

    private fun insertLongs() =
            sqlClient.insert(longWithNullable, longWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MARIADB_LONG

    fun selectAllByLongNotNull(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull eq long
                    ).fetchAll()

    fun selectAllByLongNotNullNotEq(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull notEq long
                    ).fetchAll()

    fun selectAllByLongNotNullIn(values: Sequence<Long>) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull `in` values
                    ).fetchAll()

    fun selectAllByLongNotNullInf(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull inf long
                    ).fetchAll()

    fun selectAllByLongNotNullInfOrEq(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull infOrEq long
                    ).fetchAll()

    fun selectAllByLongNotNullSup(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull sup long
                    ).fetchAll()

    fun selectAllByLongNotNullSupOrEq(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNotNull supOrEq long
                    ).fetchAll()

    fun selectAllByLongNullable(long: Long?) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable eq long
                    ).fetchAll()

    fun selectAllByLongNullableNotEq(long: Long?) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable notEq long
                    ).fetchAll()

    fun selectAllByLongNullableInf(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable inf long
                    ).fetchAll()

    fun selectAllByLongNullableInfOrEq(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable infOrEq long
                    ).fetchAll()

    fun selectAllByLongNullableSup(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable sup long
                    ).fetchAll()

    fun selectAllByLongNullableSupOrEq(long: Long) =
            (sqlClient selectFrom MARIADB_LONG
                    where MARIADB_LONG.longNullable supOrEq long
                    ).fetchAll()
}
