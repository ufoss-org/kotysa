/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.test.*

class SqLiteSelectLimitOffsetTest : AbstractSqLiteTest<LimitOffsetRepositorySelect>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = LimitOffsetRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdOffset())
                .hasSize(1)
                .containsExactly(customerUSA2)
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2`() {
        assertThat(repository.selectAllOrderByIdLimit())
                .hasSize(1)
                .containsExactly(customerFrance)
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result`() {
        assertThat(repository.selectAllLimitOffset())
                .hasSize(1)
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1`() {
        assertThat(repository.selectAllOrderByIdLimitOffset())
                .hasSize(2)
                .containsExactly(customerUSA1, customerUSA2)
    }
}

class LimitOffsetRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: SqLiteTables,
) : AbstractCustomerRepository(sqLiteOpenHelper, tables) {

    fun selectAllOrderByIdOffset() =
            (sqlClient selectFrom SqliteCustomers
                    orderByAsc SqliteCustomers.id
                    offset 2
                    ).fetchAll()

    fun selectAllOrderByIdLimit() =
            (sqlClient selectFrom SqliteCustomers
                    orderByAsc SqliteCustomers.id
                    limit 1
                    ).fetchAll()

    fun selectAllLimitOffset() =
            (sqlClient selectFrom SqliteCustomers
                    limit 1 offset 1
                    ).fetchAll()

    fun selectAllOrderByIdLimitOffset() =
            (sqlClient selectFrom SqliteCustomers
                    orderByAsc SqliteCustomers.id
                    limit 2 offset 1
                    ).fetchAll()
}
