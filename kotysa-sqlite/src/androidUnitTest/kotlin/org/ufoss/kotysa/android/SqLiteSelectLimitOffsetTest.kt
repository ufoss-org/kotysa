/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLimitOffsetTest

class SqLiteSelectLimitOffsetTest : AbstractSqLiteTest<LimitOffsetRepositorySelect>(),
    SelectLimitOffsetTest<SqliteCustomers, LimitOffsetRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = LimitOffsetRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAllOrderByIdOffset returns customerUSA2 - Android`() {
        `Verify selectAllOrderByIdOffset returns customerUSA2`()
    }

    @Test
    fun `Verify selectAllOrderByIdLimit returns customerUSA2 - Android`() {
        `Verify selectAllOrderByIdLimit returns customerUSA2`()
    }

    @Test
    fun `Verify selectAllLimitOffset returns one result - Android`() {
        `Verify selectAllLimitOffset returns one result`()
    }

    @Test
    fun `Verify selectAllOrderByIdLimitOffset returns customerUSA1 - Android`() {
        `Verify selectAllOrderByIdLimitOffset returns customerUSA1`()
    }
}

class LimitOffsetRepositorySelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectLimitOffsetRepository<SqliteCustomers>(sqLiteOpenHelper.sqlClient(tables), SqliteCustomers)
