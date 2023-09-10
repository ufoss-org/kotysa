/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectFloatTest

class VertxSqlClientSelectFloatMssqlTest : AbstractVertxSqlClientMssqlTest<SelectFloatMssqlRepository>(),
    MutinySelectFloatTest<MssqlFloats, SelectFloatMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectFloatMssqlRepository(sqlClient)
}


class SelectFloatMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
