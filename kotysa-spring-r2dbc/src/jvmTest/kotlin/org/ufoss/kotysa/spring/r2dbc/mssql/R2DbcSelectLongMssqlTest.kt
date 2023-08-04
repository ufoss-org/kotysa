/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectLongTest

@Order(2)
class R2dbcSelectLongMssqlTest : AbstractR2dbcMssqlTest<ReactorLongRepositoryMssqlSelect>(),
    ReactorSelectLongTest<MssqlLongs, ReactorLongRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ReactorLongRepositoryMssqlSelect(sqlClient)
}

class ReactorLongRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectLongRepository<MssqlLongs>(sqlClient, MssqlLongs)
