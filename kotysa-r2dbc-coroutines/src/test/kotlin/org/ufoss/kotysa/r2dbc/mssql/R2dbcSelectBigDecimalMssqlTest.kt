/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalMssqlTest : AbstractR2dbcMssqlTest<SelectBigDecimalMssqlRepository>(),
    CoroutinesSelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectBigDecimalMssqlRepository(sqlClient)
}


class SelectBigDecimalMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
