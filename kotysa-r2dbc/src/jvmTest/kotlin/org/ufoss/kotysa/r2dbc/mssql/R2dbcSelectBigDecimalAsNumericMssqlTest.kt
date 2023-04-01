/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class R2dbcSelectBigDecimalAsNumericMssqlTest : AbstractR2dbcMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    CoroutinesSelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectBigDecimalAsNumericMssqlRepository(sqlClient)
}


class SelectBigDecimalAsNumericMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
