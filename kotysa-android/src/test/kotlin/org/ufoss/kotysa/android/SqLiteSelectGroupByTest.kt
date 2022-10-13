/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.SqliteCustomers
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectGroupByTest

class SqLiteSelectGroupByTest : AbstractSqLiteTest<GroupByRepositorySelect>(),
    SelectGroupByTest<SqliteCustomers, GroupByRepositorySelect, AndroidTransaction> {

    override fun getRepository(sqLiteTables: SqLiteTables) = GroupByRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group - Android`() {
        `Verify selectCountCustomerGroupByCountry counts and group`()
    }
}

class GroupByRepositorySelect(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : SelectGroupByRepository<SqliteCustomers>(sqLiteOpenHelper.sqlClient(tables), SqliteCustomers)
