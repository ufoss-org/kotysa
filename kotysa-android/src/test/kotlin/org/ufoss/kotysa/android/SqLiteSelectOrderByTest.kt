/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

class SqLiteSelectOrderByTest : AbstractSqLiteTest<OrderByRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = OrderByRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAsc())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`() {
        assertThat(repository.selectCustomerOrderByAgeAndIdAsc())
                .hasSize(3)
                .containsExactly(customerFrance, customerUSA2, customerUSA1)
    }
}

class OrderByRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables,
) : AbstractCustomerRepository(sqLiteOpenHelper, tables) {

    fun selectCustomerOrderByAgeAsc() =
            (sqlClient selectFrom SqliteCustomers
                    orderByAsc SqliteCustomers.age
                    ).fetchAll()

    fun selectCustomerOrderByAgeAndIdAsc() =
            (sqlClient selectFrom SqliteCustomers
                    orderByAsc SqliteCustomers.age andAsc SqliteCustomers.id
                    ).fetchAll()
}
