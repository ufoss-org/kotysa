/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class VertxCoroutinesSelectBigDecimalAsNumericMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectBigDecimalAsNumericMssqlRepository>(),
    CoroutinesSelectBigDecimalAsNumericTest<MssqlBigDecimalAsNumerics, SelectBigDecimalAsNumericMssqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectBigDecimalAsNumericMssqlRepository(sqlClient)
}


class SelectBigDecimalAsNumericMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MssqlBigDecimalAsNumerics>(sqlClient, MssqlBigDecimalAsNumerics)
