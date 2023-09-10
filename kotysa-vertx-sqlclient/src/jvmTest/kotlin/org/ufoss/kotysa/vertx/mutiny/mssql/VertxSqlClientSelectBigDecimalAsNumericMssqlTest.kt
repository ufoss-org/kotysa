/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalAsNumericTest

class VertxSqlClientSelectBigDecimalAsNumericMssqlTest : AbstractVertxSqlClientMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    MutinySelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectBigDecimalAsNumericMssqlRepository(sqlClient)
}


class SelectBigDecimalAsNumericMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
