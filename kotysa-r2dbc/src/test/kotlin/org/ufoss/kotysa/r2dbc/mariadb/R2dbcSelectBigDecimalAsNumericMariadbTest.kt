/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class R2dbcSelectBigDecimalAsNumericMariadbTest : AbstractR2dbcMariadbTest<BigDecimalAsNumericRepositoryMariadbSelect>(),
    CoroutinesSelectBigDecimalAsNumericTest<MariadbBigDecimalAsNumerics, BigDecimalAsNumericRepositoryMariadbSelect,
            R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalAsNumericRepositoryMariadbSelect(sqlClient)
}

class BigDecimalAsNumericRepositoryMariadbSelect(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<MariadbBigDecimalAsNumerics>(sqlClient, MariadbBigDecimalAsNumerics)
