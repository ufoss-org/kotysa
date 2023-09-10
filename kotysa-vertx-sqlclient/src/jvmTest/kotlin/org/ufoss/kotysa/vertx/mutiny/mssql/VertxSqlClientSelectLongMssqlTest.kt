/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.MutinyVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectLongTest

@Order(2)
class VertxSqlClientSelectLongMssqlTest : AbstractVertxSqlClientMssqlTest<SelectLongRepositoryMssqlSelect>(),
    MutinySelectLongTest<MssqlLongs, SelectLongRepositoryMssqlSelect> {
    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectLongRepositoryMssqlSelect(sqlClient)
}

class SelectLongRepositoryMssqlSelect(sqlClient: MutinyVertxSqlClient) :
    MutinySelectLongRepository<MssqlLongs>(sqlClient, MssqlLongs)
