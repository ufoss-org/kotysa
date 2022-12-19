/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2BigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalAsNumericTest

class R2dbcSelectBigDecimalAsNumericH2Test : AbstractR2dbcH2Test<BigDecimalAsNumericRepositoryH2Select>(),
    CoroutinesSelectBigDecimalAsNumericTest<H2BigDecimalAsNumerics, BigDecimalAsNumericRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalAsNumericRepositoryH2Select(sqlClient)
}

class BigDecimalAsNumericRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalAsNumericRepository<H2BigDecimalAsNumerics>(sqlClient, H2BigDecimalAsNumerics)
