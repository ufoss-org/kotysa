/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalTest

class VertxSqlClientSelectBigDecimalMssqlTest : AbstractVertxSqlClientMssqlTest<SelectBigDecimalMssqlRepository>(),
    MutinySelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectBigDecimalMssqlRepository(sqlClient)
}


class SelectBigDecimalMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
