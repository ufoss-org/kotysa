/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteOpenHelper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.ufoss.kotysa.Tables
import org.ufoss.kotysa.test.*

class SqLiteSelectGroupByTest : AbstractSqLiteTest<GroupByRepositorySelect>() {

    override fun getRepository(sqLiteTables: Tables) = GroupByRepositorySelect(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectCountCustomerGroupByCountry counts and group`() {
        assertThat(repository.selectCountCustomerGroupByCountry())
                .hasSize(2)
                .containsExactly(Pair(1, customerFrance.country), Pair(2, customerUSA1.country))
    }
}

class GroupByRepositorySelect(
        sqLiteOpenHelper: SQLiteOpenHelper,
        tables: Tables,
) : AbstractCustomerRepository(sqLiteOpenHelper, tables) {

    fun selectCountCustomerGroupByCountry() =
            (sqlClient selectCount SQLITE_CUSTOMER.id and SQLITE_CUSTOMER.country
                    from SQLITE_CUSTOMER
                    groupBy SQLITE_CUSTOMER.country
                    ).fetchAll()
}
