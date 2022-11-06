/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.postgresql

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.test.*

@Order(1)
class R2dbcSelectIntPostgresqlTest : AbstractR2dbcPostgresqlTest<IntRepositoryPostgresqlSelect>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = IntRepositoryPostgresqlSelect(sqlClient)

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
    fun `Verify selectAllByIntNotNull finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNull(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds intWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullNotEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullIn finds both`() = runTest {
        val seq = sequenceOf(intWithNullable.intNotNull, intWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntNotNullIn(seq).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(intWithNullable, intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullInf(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullInf(10).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds intWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds intWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullSup(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullSup(12).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds intWithoutNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullable(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds intWithoutNullable`() = runTest {
        assertThat(repository.selectAllByIntNullable(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() = runTest {
        assertThat(repository.selectAllByIntNullableNotEq(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableNotEq(null).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableInf(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableInf(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableInfOrEq(7).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds intWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableInfOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableSup(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableSup(6).toList())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithNullable`() = runTest {
        assertThat(repository.selectAllByIntNullableSupOrEq(5).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds intWithNullable when equals`() = runTest {
        assertThat(repository.selectAllByIntNullableSupOrEq(6).toList())
            .hasSize(1)
            .containsExactlyInAnyOrder(intWithNullable)
    }
}


class IntRepositoryPostgresqlSelect(private val sqlClient: R2dbcSqlClient) : Repository {

    override fun init() = runBlocking {
        createTables()
        insertInts()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTable PostgresqlInts
    }

    private suspend fun insertInts() {
        sqlClient.insert(intWithNullable, intWithoutNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom PostgresqlInts

    fun selectAllByIntNotNull(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull eq int
                ).fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull notEq int
                ).fetchAll()

    fun selectAllByIntNotNullIn(values: Sequence<Int>) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull `in` values
                ).fetchAll()

    fun selectAllByIntNotNullInf(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull inf int
                ).fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull infOrEq int
                ).fetchAll()

    fun selectAllByIntNotNullSup(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull sup int
                ).fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNotNull supOrEq int
                ).fetchAll()

    fun selectAllByIntNullable(int: Int?) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable eq int
                ).fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable notEq int
                ).fetchAll()

    fun selectAllByIntNullableInf(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable inf int
                ).fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable infOrEq int
                ).fetchAll()

    fun selectAllByIntNullableSup(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable sup int
                ).fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) =
        (sqlClient selectFrom PostgresqlInts
                where PostgresqlInts.intNullable supOrEq int
                ).fetchAll()
}
