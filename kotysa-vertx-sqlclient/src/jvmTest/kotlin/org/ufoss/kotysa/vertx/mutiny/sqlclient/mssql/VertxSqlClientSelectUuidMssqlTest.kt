/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectUuidRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectUuidTest

class VertxSqlClientSelectUuidMssqlTest :
    AbstractVertxSqlClientMssqlTest<UuidRepositoryMssqlSelect>(),
    MutinySelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)
