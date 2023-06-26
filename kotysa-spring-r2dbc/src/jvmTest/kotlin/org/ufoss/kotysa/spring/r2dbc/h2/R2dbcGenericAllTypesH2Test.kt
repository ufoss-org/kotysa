/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorGenericAllTypesTest

class R2dbcGenericAllTypesH2Test : AbstractR2dbcH2Test<ReactorGenericAllTypesRepository>(),
    ReactorGenericAllTypesTest<ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        ReactorGenericAllTypesRepository(sqlClient)
}
