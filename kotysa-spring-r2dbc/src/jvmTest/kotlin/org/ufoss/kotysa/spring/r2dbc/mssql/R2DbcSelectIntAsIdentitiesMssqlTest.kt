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
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectIntAsIdentitiesTest

@Order(1)
class R2DbcSelectIntAsIdentitiesMssqlTest : AbstractR2dbcMssqlTest<ReactorSelectIntRepositoryMssqlSelect>(),
    ReactorSelectIntAsIdentitiesTest<MssqlIntAsIdentities, ReactorSelectIntRepositoryMssqlSelect, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        ReactorSelectIntRepositoryMssqlSelect(sqlClient)
}

class ReactorSelectIntRepositoryMssqlSelect(sqlClient: ReactorSqlClient) :
    ReactorSelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
