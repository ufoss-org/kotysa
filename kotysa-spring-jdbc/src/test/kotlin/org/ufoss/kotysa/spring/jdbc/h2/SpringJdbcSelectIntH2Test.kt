/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.test.*


class SpringJdbcSelectIntH2Test : AbstractSpringJdbcH2Test<IntRepositoryH2Select>() {
    override val context = startContext<IntRepositoryH2Select>()

    override val repository = getContextRepository<IntRepositoryH2Select>()

    private val h2IntWithNullable = H2Int(
            org.ufoss.kotysa.test.h2IntWithNullable.intNotNull,
            org.ufoss.kotysa.test.h2IntWithNullable.intNullable,
            1)

    private val h2IntWithoutNullable = H2Int(
            org.ufoss.kotysa.test.h2IntWithoutNullable.intNotNull,
            org.ufoss.kotysa.test.h2IntWithoutNullable.intNullable,
            2)

    @Test
    fun `Verify selectAllByIntNotNull finds h2IntWithNullable`() {
        assertThat(repository.selectAllByIntNotNull(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullNotEq finds h2IntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullNotEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntegerNotNullIn finds both`() {
        val seq = sequenceOf(h2IntWithNullable.intNotNull, h2IntWithoutNullable.intNotNull)
        assertThat(repository.selectAllByIntegerNotNullIn(seq))
                .hasSize(2)
                .containsExactlyInAnyOrder(h2IntWithNullable, h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds h2IntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInf(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullInf(10))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds h2IntWithNullable`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullInfOrEq finds h2IntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullInfOrEq(10))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds h2IntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSup(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNotNullSup(12))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds h2IntWithoutNullable`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(11))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNotNullSupOrEq finds h2IntWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNotNullSupOrEq(12))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByIntNullable(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByIntNullable(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithoutNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByIntNullableNotEq(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableNotEq finds no results`() {
        assertThat(repository.selectAllByIntNullableNotEq(null))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds h2IntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInf(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableInf(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds h2IntWithNullable`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(7))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableInfOrEq finds h2IntWithNullable when equals`() {
        assertThat(repository.selectAllByIntNullableInfOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds h2IntWithoutNullable`() {
        assertThat(repository.selectAllByIntNullableSup(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByIntNullableSup(6))
                .isEmpty()
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds h2IntWithoutNullable`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(5))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }

    @Test
    fun `Verify selectAllByIntNullableSupOrEq finds h2IntWithoutNullable when equals`() {
        assertThat(repository.selectAllByIntNullableSupOrEq(6))
                .hasSize(1)
                .containsExactlyInAnyOrder(h2IntWithNullable)
    }
}


class IntRepositoryH2Select(client: JdbcOperations) : Repository {

    private val sqlClient = client.sqlClient(h2Tables)

    override fun init() {
        createTables()
        insertInts()
    }

    override fun delete() {
        deleteAll()
    }

    private fun createTables() =
            sqlClient.createTable<H2Int>()

    private fun insertInts() = sqlClient.insert(h2IntWithNullable, h2IntWithoutNullable)

    private fun deleteAll() = sqlClient.deleteAllFromTable<H2Int>()

    fun selectAllByIntNotNull(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] eq int }
            .fetchAll()

    fun selectAllByIntNotNullNotEq(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] notEq int }
            .fetchAll()

    fun selectAllByIntegerNotNullIn(values: Sequence<Int>) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] `in` values }
            .fetchAll()

    fun selectAllByIntNotNullInf(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] inf int }
            .fetchAll()

    fun selectAllByIntNotNullInfOrEq(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] infOrEq int }
            .fetchAll()

    fun selectAllByIntNotNullSup(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] sup int }
            .fetchAll()

    fun selectAllByIntNotNullSupOrEq(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNotNull] supOrEq int }
            .fetchAll()

    fun selectAllByIntNullable(int: Int?) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] eq int }
            .fetchAll()

    fun selectAllByIntNullableNotEq(int: Int?) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] notEq int }
            .fetchAll()

    fun selectAllByIntNullableInf(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] inf int }
            .fetchAll()

    fun selectAllByIntNullableInfOrEq(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] infOrEq int }
            .fetchAll()

    fun selectAllByIntNullableSup(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] sup int }
            .fetchAll()

    fun selectAllByIntNullableSupOrEq(int: Int) = sqlClient.select<H2Int>()
            .where { it[H2Int::intNullable] supOrEq int }
            .fetchAll()
}
