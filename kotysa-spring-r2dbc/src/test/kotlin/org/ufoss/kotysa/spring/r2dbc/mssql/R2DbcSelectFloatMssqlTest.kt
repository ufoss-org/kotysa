/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatMssqlTest :
    AbstractR2dbcMssqlTest<SelectFloatMssqlRepository>(),
    ReactorSelectFloatTest<MssqlFloats, SelectFloatMssqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        SelectFloatMssqlRepository(sqlClient)
}


class SelectFloatMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorSelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
