/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesTest

class R2dbcGenericAllTypesMssqlTest : AbstractR2dbcMssqlTest<CoroutinesGenericAllTypesRepository>(),
    CoroutinesGenericAllTypesTest<R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = CoroutinesGenericAllTypesRepository(sqlClient)
}
