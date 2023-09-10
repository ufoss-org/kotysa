/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectDoubleTest

class VertxSqlClientSelectDoubleMssqlTest : AbstractVertxSqlClientMssqlTest<SelectDoubleMssqlRepository>(),
    MutinySelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectDoubleMssqlRepository(sqlClient)
}


class SelectDoubleMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
