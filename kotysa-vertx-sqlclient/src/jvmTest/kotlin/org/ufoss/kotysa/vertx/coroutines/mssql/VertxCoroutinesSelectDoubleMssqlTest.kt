/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class VertxCoroutinesSelectDoubleMssqlTest : AbstractVertxCoroutinesMssqlTest<SelectDoubleMssqlRepository>(),
    CoroutinesSelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository, Transaction> {

    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = SelectDoubleMssqlRepository(sqlClient)
}


class SelectDoubleMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesSelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
