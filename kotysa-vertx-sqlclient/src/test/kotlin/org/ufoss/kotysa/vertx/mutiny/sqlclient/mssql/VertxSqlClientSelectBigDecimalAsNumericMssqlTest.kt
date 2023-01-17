/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.mssql

import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.mutiny.sqlclient.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.MutinySelectBigDecimalAsNumericTest

class VertxSqlClientSelectBigDecimalAsNumericMssqlTest : AbstractVertxSqlClientMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository> {

    override fun instantiateRepository(sqlClient: VertxSqlClient) = SelectBigDecimalAsNumericMssqlRepository(sqlClient)
}


class SelectBigDecimalAsNumericMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
