/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteOpenHelper
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.android.transaction.AndroidTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

class SqLiteInsertTest : AbstractSqLiteTest<RepositorySqLiteInsert>(),
    InsertTest<SqliteInts, SqliteLongs, SqliteCustomers, SqliteIntNonNullIds, SqliteLongNonNullIds,
            RepositorySqLiteInsert, AndroidTransaction> {
    override fun getRepository(sqLiteTables: SqLiteTables) = RepositorySqLiteInsert(dbHelper, sqLiteTables)
    override val exceptionClass = SQLiteConstraintException::class

    @Test
    fun `Verify insertCustomers works correctly - Android`() {
        `Verify insertCustomers works correctly`()
    }

    @Test
    fun `Verify insertAndReturnCustomers works correctly - Android`() {
        `Verify insertAndReturnCustomers works correctly`()
    }

    @Test
    fun `Verify insertAndReturnInt auto-generated works correctly - Android`() {
        `Verify insertAndReturnInt auto-generated works correctly`()
    }

    @Test
    fun `Verify insertAndReturnInt not auto-generated works correctly - Android`() {
        `Verify insertAndReturnInt not auto-generated works correctly`()
    }

    @Test
    fun `Verify insertAndReturnIntNullId auto-generated works correctly - Android`() {
        `Verify insertAndReturnIntNullId auto-generated works correctly`()
    }

    @Test
    fun `Verify insertAndReturnIntNullId not auto-generated works correctly - Android`() {
        `Verify insertAndReturnIntNullId not auto-generated works correctly`()
    }

    @Test
    fun `Verify insertAndReturnLongs works correctly - Android`() {
        `Verify insertAndReturnLongs works correctly`()
    }

    @Test
    fun `Verify insertAndReturnLongNullIds works correctly - Android`() {
        `Verify insertAndReturnLongNullIds works correctly`()
    }

    @Test
    fun `Verify insertCustomer fails if duplicate name - Android`() {
        `Verify insertCustomer fails if duplicate name`()
    }
}

class RepositorySqLiteInsert(sqLiteOpenHelper: SQLiteOpenHelper, tables: SqLiteTables) :
    InsertRepository<SqliteInts, SqliteLongs, SqliteCustomers, SqliteIntNonNullIds, SqliteLongNonNullIds>(
        sqLiteOpenHelper.sqlClient(tables),
        SqliteInts,
        SqliteLongs,
        SqliteCustomers,
        SqliteIntNonNullIds,
        SqliteLongNonNullIds
    )
