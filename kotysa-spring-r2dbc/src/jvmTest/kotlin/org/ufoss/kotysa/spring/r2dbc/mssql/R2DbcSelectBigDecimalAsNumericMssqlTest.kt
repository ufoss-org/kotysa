/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericMssqlTest :
    AbstractR2dbcMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    ReactorSelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository,
            ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        SelectBigDecimalAsNumericMssqlRepository(sqlClient)
}


class SelectBigDecimalAsNumericMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorSelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
