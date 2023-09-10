/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesGenericAllTypesTest

class VertxCoroutinesGenericAllTypesMssqlTest : AbstractVertxCoroutinesMssqlTest<CoroutinesGenericAllTypesRepository>(),
    CoroutinesGenericAllTypesTest<Transaction> {
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = CoroutinesGenericAllTypesRepository(sqlClient)
}
