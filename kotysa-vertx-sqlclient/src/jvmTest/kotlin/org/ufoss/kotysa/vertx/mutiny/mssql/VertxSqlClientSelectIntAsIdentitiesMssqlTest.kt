/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.MssqlIntAsIdentities
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntAsIdentitiesMssqlTest :
    AbstractVertxSqlClientMssqlTest<SelectIntAsIdentitiesRepositoryMssqlSelect>(),
    MutinySelectIntAsIdentitiesTest<MssqlIntAsIdentities, SelectIntAsIdentitiesRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) =
        SelectIntAsIdentitiesRepositoryMssqlSelect(sqlClient)
}

class SelectIntAsIdentitiesRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
