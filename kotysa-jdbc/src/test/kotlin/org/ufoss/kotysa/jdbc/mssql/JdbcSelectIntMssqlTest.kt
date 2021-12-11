/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.sqlClient
import org.ufoss.kotysa.test.*
import java.sql.Connection

@Order(1)
class JdbcSelectIntMssqlTest : AbstractJdbcMssqlTest<IntRepositoryMssqlSelect>() {
    override fun instantiateRepository(connection: Connection) = IntRepositoryMssqlSelect(connection)

    private val intWithNullable = IntEntity(
            org.ufoss.kotysa.test.intWithNullable.intNotNull,
            org.ufoss.kotysa.test.intWithNullable.intNullable,
            1
    )

    private val intWithoutNullable = IntEntity(
            org.ufoss.kotysa.test.intWithoutNullable.intNotNull,
            org.ufoss.kotysa.test.intWithoutNullable.intNullable,
            2
    )

    @Test
    fun `Verify selectAllByIntNotNull finds intWithNullable`() {
        assertThat(repository.selectAllByIntNotNull(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullNotEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() {
        val seq = sequenceOf(intWithNullable.intNotNull, intWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(intWithNullable, intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInf(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullInf(10))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSup(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullSup(12))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullable(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithoutNullable`() {
        assertThat(repository.selectAllByIntNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntNullableNotEq(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullableInf(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableInf(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullableSup(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableSup(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithNullable`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(intWithNullable)
    }
}


class IntRepositoryMssqlSelect(connection: Connection) : Repository {

    private val sqlClient = connection.sqlClient(mssqlTables)

    override fun init() {
        createTables()
        insertInts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() {
        sqlClient createTable MSSQL_INT
    }

    private fun insertInts() {
        sqlClient.insert(intWithNullable, intWithoutNullable)
    }

    private fun deleteAll() = sqlClient deleteAllFrom MSSQL_INT

    fun selectAllByIntNotNull(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull eq int
                    ).fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull notEq int
                    ).fetchAll()

    fun selectAllByIntNotNullIn(values: Sequence<Int>) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull `in` values
                    ).fetchAll()

    fun selectAllByIntNotNullInf(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull inf int
                    ).fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull infOrEq int
                    ).fetchAll()

    fun selectAllByIntNotNullSup(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull sup int
                    ).fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNotNull supOrEq int
                    ).fetchAll()

    fun selectAllByIntNullable(int: Int?) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable eq int
                    ).fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable notEq int
                    ).fetchAll()

    fun selectAllByIntNullableInf(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable inf int
                    ).fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable infOrEq int
                    ).fetchAll()

    fun selectAllByIntNullableSup(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable sup int
                    ).fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) =
            (sqlClient selectFrom MSSQL_INT
                    where MSSQL_INT.intNullable supOrEq int
                    ).fetchAll()
}
