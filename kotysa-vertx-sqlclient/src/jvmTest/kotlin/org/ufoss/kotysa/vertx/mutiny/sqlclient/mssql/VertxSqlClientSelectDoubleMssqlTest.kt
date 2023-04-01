/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectDoubleTest

class VertxSqlClientSelectDoubleMssqlTest : AbstractVertxSqlClientMssqlTest<SelectDoubleMssqlRepository>(),
    MutinySelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectDoubleMssqlRepository(sqlClient)
}


class SelectDoubleMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
