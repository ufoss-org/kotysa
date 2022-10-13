/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectMinMaxAvgSumTest

class SqLiteSelectMinMaxAvgSumTest : AbstractSqLiteTest<MinMaxAvgSumRepositorySelect>(),
    SelectMinMaxAvgSumTest<SqliteCustomers, MinMaxAvgSumRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = MinMaxAvgSumRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCustomerMinAge returns 19 - Android`() {
        `Verify selectCustomerMinAge returns 19`()
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21 - Android`() {
        `Verify selectCustomerMaxAge returns 21`()
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20 - Android`() {
        `Verify selectCustomerAvgAge returns 20`()
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60 - Android`() {
        `Verify selectCustomerSumAge returns 60`()
    }
}

class MinMaxAvgSumRepositorySelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectMinMaxAvgSumRepository<SqliteCustomers>(sqLiteOpenHelper.sqlClient(tables), SqliteCustomers)
