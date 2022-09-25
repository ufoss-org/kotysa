/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mariadb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

@Order(2)
class VertxSqlClientSelectLongMariadbTest : AbstractVertxSqlClientMariadbTest<LongRepositoryMariadbSelect>() {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = LongRepositoryMariadbSelect(sqlClient)

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
        assertThat(repository.selectAllByLongNotNull(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullNotEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullNotEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullIn finds both`() {
        val seq = sequenceOf(longWithNullable.longNotNull, longWithoutNullable.longNotNull)
        assertThat(repository.selectAllByLongNotNullIn(seq).await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(longWithNullable, longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInf(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullInf(10).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullInfOrEq(10).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSup(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNotNullSup(12).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(11).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNotNullSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNotNullSupOrEq(12).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithNullable`() {
        assertThat(repository.selectAllByLongNullable(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullable finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullable(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithoutNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds h2UuidWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableNotEq(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableNotEq finds no results`() {
        assertThat(repository.selectAllByLongNullableNotEq(null).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInf(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInf finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableInf(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(7).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableInfOrEq finds sqLiteIntegerWithNullable when equals`() {
        assertThat(repository.selectAllByLongNullableInfOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSup(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSup finds no results when equals`() {
        assertThat(repository.selectAllByLongNullableSup(6).await().indefinitely())
            .isEmpty()
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(5).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }

    @Test
    fun `Verify selectAllByLongNullableSupOrEq finds sqLiteIntegerWithoutNullable when equals`() {
        assertThat(repository.selectAllByLongNullableSupOrEq(6).await().indefinitely())
            .hasSize(1)
            .containsExactlyInAnyOrder(longWithNullable)
    }
}


class LongRepositoryMariadbSelect(private val sqlClient: VertxSqlClient) : Repository {

    override fun init() {
        createTables()
            .chain { -> insertLongs() }
            .await().indefinitely()
    }

    override fun delete() {
        deleteAll()
            .await().indefinitely()
    }

    private fun createTables() = sqlClient createTable MariadbLongs

    private fun insertLongs() =
        sqlClient.insert(longWithNullable, longWithoutNullable)

    private fun deleteAll() = sqlClient deleteAllFrom MariadbLongs

    fun selectAllByLongNotNull(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull eq long
                ).fetchAll()

    fun selectAllByLongNotNullNotEq(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull notEq long
                ).fetchAll()

    fun selectAllByLongNotNullIn(values: Sequence<Long>) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull `in` values
                ).fetchAll()

    fun selectAllByLongNotNullInf(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull inf long
                ).fetchAll()

    fun selectAllByLongNotNullInfOrEq(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull infOrEq long
                ).fetchAll()

    fun selectAllByLongNotNullSup(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull sup long
                ).fetchAll()

    fun selectAllByLongNotNullSupOrEq(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNotNull supOrEq long
                ).fetchAll()

    fun selectAllByLongNullable(long: Long?) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable eq long
                ).fetchAll()

    fun selectAllByLongNullableNotEq(long: Long?) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable notEq long
                ).fetchAll()

    fun selectAllByLongNullableInf(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable inf long
                ).fetchAll()

    fun selectAllByLongNullableInfOrEq(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable infOrEq long
                ).fetchAll()

    fun selectAllByLongNullableSup(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable sup long
                ).fetchAll()

    fun selectAllByLongNullableSupOrEq(long: Long) =
        (sqlClient selectFrom MariadbLongs
                where MariadbLongs.longNullable supOrEq long
                ).fetchAll()
}
