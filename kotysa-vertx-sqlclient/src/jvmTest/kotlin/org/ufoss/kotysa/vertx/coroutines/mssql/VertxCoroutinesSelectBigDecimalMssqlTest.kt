/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlBigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class VertxCoroutinesSelectBigDecimalMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectBigDecimalMssqlRepository>(),
    CoroutinesSelectBigDecimalTest<MssqlBigDecimals, SelectBigDecimalMssqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectBigDecimalMssqlRepository(sqlClient)
}


class SelectBigDecimalMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalRepository<MssqlBigDecimals>(sqlClient, MssqlBigDecimals)
