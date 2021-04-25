/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*
import java.math.MathContext

class SqLiteSelectMinMaxAvgSumTest : AbstractSqLiteTest<MinMaxAvgSumRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = MinMaxAvgSumRepositorySelect(dbHelper, sqLiteTables)

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
        tables: Tables,
) : AbstractCustomerRepository(sqLiteOpenHelper, tables) {

    fun selectCustomerMinAge() =
            (sqlClient selectMin SQLITE_CUSTOMER.age
                    from SQLITE_CUSTOMER
                    ).fetchOne()

    fun selectCustomerMaxAge() =
            (sqlClient selectMax SQLITE_CUSTOMER.age
                    from SQLITE_CUSTOMER
                    ).fetchOne()

    fun selectCustomerAvgAge() =
            (sqlClient selectAvg SQLITE_CUSTOMER.age
                    from SQLITE_CUSTOMER
                    ).fetchOne()

    fun selectCustomerSumAge() =
            (sqlClient selectSum SQLITE_CUSTOMER.age
                    from SQLITE_CUSTOMER
                    ).fetchOne()
}
