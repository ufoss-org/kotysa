/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectIntAsIdentitiesTest

@Order(1)
class VertxSqlClientSelectIntMssqlTest : AbstractVertxSqlClientMssqlTest<SelectIntRepositoryMssqlSelect>(),
    MutinySelectIntAsIdentitiesTest<MssqlIntAsIdentities, SelectIntRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectIntRepositoryMssqlSelect(sqlClient)
}

class SelectIntRepositoryMssqlSelect(sqlClient: VertxSqlClient) :
    MutinySelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
