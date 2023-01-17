/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatMssqlTest : AbstractVertxSqlClientMssqlTest<SelectFloatMssqlRepository>(),
    MutinySelectFloatTest<MssqlFloats, SelectFloatMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectFloatMssqlRepository(sqlClient)
}


class SelectFloatMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
