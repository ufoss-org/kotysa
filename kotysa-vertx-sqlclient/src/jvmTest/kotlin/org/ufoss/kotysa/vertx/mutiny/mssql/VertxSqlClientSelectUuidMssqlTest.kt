/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectUuidRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectUuidTest

class VertxSqlClientSelectUuidMssqlTest :
    AbstractVertxSqlClientMssqlTest<UuidRepositoryMssqlSelect>(),
    MutinySelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)
