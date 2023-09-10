/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.mssql

import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.vertx.*
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalRepository
import org.ufoss.kotysa.vertx.mutiny.repositories.MutinySelectBigDecimalTest

class VertxSqlClientSelectBigDecimalMssqlTest : AbstractVertxSqlClientMssqlTest<SelectBigDecimalMssqlRepository>(),
    MutinySelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository> {

    override fun instantiateRepository(sqlClient: MutinyVertxSqlClient) = SelectBigDecimalMssqlRepository(sqlClient)
}


class SelectBigDecimalMssqlRepository(sqlClient: MutinySqlClient) :
    MutinySelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
