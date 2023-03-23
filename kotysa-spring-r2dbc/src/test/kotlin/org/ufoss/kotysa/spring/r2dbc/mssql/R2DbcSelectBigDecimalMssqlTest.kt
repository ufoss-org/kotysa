/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalMssqlTest :
    AbstractR2dbcMssqlTest<SelectBigDecimalMssqlRepository>(),
    ReactorSelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        SelectBigDecimalMssqlRepository(sqlClient)
}


class SelectBigDecimalMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorSelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
