/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyGenericAllTypesRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinyGenericAllTypesTest

class VertxSqlClientGenericAllTypesMssqlTest : AbstractVertxSqlClientMssqlTest<MutinyGenericAllTypesRepository>(),
    MutinyGenericAllTypesTest {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = MutinyGenericAllTypesRepository(sqlClient)
}
