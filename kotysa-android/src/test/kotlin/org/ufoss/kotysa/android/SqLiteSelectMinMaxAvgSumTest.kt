/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.test.*

class SqLiteSelectMinMaxAvgSumTest : AbstractSqLiteTest<MinMaxAvgSumRepositorySelect>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = MinMaxAvgSumRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCustomerMinAge returns 19`() {
        assertThat(repository.selectCustomerMinAge())
                .isEqualTo(19)
    }

    @Test
    fun `Verify selectCustomerMaxAge returns 21`() {
        assertThat(repository.selectCustomerMaxAge())
                .isEqualTo(21)
    }

    @Test
    fun `Verify selectCustomerAvgAge returns 20`() {
        assertThat(repository.selectCustomerAvgAge())
                .isEqualByComparingTo(20.toBigDecimal())
    }

    @Test
    fun `Verify selectCustomerSumAge returns 60`() {
        assertThat(repository.selectCustomerSumAge())
                .isEqualTo(60)
    }
}

class MinMaxAvgSumRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: SqLiteTables,
) : AbstractCustomerRepository(sqLiteOpenHelper, tables) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin SqliteCustomers.age
                    from SqliteCustomers
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax SqliteCustomers.age
                    from SqliteCustomers
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg SqliteCustomers.age
                    from SqliteCustomers
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum SqliteCustomers.age
                    from SqliteCustomers
                    ).fetchOne()
}
