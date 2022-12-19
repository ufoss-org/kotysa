/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlFloats
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectFloatTest

class R2dbcSelectFloatMssqlTest : AbstractR2dbcMssqlTest<SelectFloatMssqlRepository>(),
    CoroutinesSelectFloatTest<MssqlFloats, SelectFloatMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = SelectFloatMssqlRepository(sqlClient)
}


class SelectFloatMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectFloatRepository<MssqlFloats>(sqlClient, MssqlFloats)
