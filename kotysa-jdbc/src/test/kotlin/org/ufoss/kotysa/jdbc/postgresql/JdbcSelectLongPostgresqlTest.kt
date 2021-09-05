/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection


class JdbcSelectLongPostgresqlTest : AbstractJdbcPostgresqlTest<LongRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(connection: Connection) = LongRepositoryPostgresqlSelect(connection)

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
        assertThat(repository.selectAllByLongNotNull(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullNotEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        assertThat(repository.selectAllByLongNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInf(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullInf(10))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSup(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullSup(12))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(12))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLongNullable(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableNotEq(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLongNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInf(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableInf(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSup(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableSup(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(longWithNullable)
    }
}


class LongRepositoryPostgresqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(postgresqlTables)

    override fun init() {
        createTables()
        insertLongs()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable POSTGRESQL_LONG
    }

    private fun insertLongs() {
        sqlClient.insert(longWithNullable, longWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom POSTGRESQL_LONG

    fun selectAllByLongNotNull(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull eq long
                    ).fetchAll()

    fun selectAllByLongNotNullNotEq(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull notEq long
                    ).fetchAll()

    fun selectAllByLongNotNullIn(values: Sequence<Long>) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull `in` values
                    ).fetchAll()

    fun selectAllByLongNotNullInf(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull inf long
                    ).fetchAll()

    fun selectAllByLongNotNullInfOrEq(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull infOrEq long
                    ).fetchAll()

    fun selectAllByLongNotNullSup(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull sup long
                    ).fetchAll()

    fun selectAllByLongNotNullSupOrEq(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNotNull supOrEq long
                    ).fetchAll()

    fun selectAllByLongNullable(long: Long?) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable eq long
                    ).fetchAll()

    fun selectAllByLongNullableNotEq(long: Long?) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable notEq long
                    ).fetchAll()

    fun selectAllByLongNullableInf(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable inf long
                    ).fetchAll()

    fun selectAllByLongNullableInfOrEq(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable infOrEq long
                    ).fetchAll()

    fun selectAllByLongNullableSup(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable sup long
                    ).fetchAll()

    fun selectAllByLongNullableSupOrEq(long: Long) =
            (sqlClient selectFrom POSTGRESQL_LONG
                    where POSTGRESQL_LONG.longNullable supOrEq long
                    ).fetchAll()
}
