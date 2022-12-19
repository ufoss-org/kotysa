/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlDoubles
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectDoubleTest

class R2dbcSelectDoubleMssqlTest : AbstractR2dbcMssqlTest<SelectDoubleMssqlRepository>(),
    CoroutinesSelectDoubleTest<MssqlDoubles, SelectDoubleMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectDoubleMssqlRepository(sqlClient)
}


class SelectDoubleMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectDoubleRepository<MssqlDoubles>(sqlClient, MssqlDoubles)
