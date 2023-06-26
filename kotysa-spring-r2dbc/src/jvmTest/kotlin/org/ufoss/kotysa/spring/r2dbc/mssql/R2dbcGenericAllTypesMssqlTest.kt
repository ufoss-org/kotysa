/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesTest

class R2dbcGenericAllTypesMssqlTest : AbstractR2dbcMssqlTest<ReactorGenericAllTypesRepository>(),
    ReactorGenericAllTypesTest<ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ReactorGenericAllTypesRepository(sqlClient)
}
