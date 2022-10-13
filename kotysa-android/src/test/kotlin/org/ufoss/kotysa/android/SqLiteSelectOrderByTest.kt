/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOrderByTest

class SqLiteSelectOrderByTest : AbstractSqLiteTest<OrderByRepositorySelect>(),
    SelectOrderByTest<SqliteCustomers, OrderByRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = OrderByRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC - Android`() {
        `Verify selectCustomerOrderByAgeAsc returns all customers ordered by age ASC`()
    }

    @Test
    fun `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC - Android`() {
        `Verify selectCustomerOrderByAgeAndIdAsc returns all customers ordered by age and id ASC`()
    }
}

class OrderByRepositorySelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectOrderByRepository<SqliteCustomers>(sqLiteOpenHelper.sqlClient(tables), SqliteCustomers)
