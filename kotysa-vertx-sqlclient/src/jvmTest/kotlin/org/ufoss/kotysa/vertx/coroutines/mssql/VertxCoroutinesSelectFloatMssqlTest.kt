/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class VertxCoroutinesSelectFloatMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectFloatMssqlRepository>(),
    CoroutinesSelectFloatTest<MssqlFloats, SelectFloatMssqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectFloatMssqlRepository(sqlClient)
}


class SelectFloatMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
