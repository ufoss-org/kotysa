/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleMssqlTest :
    AbstractR2dbcMssqlTest<SelectDoubleMssqlRepository>(),
    ReactorSelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        SelectDoubleMssqlRepository(sqlClient)
}


class SelectDoubleMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorSelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
