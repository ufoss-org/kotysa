/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2BigDecimalAsNumerics
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericH2Test : AbstractR2dbcH2Test<BigDecimalAsNumericH2Repository>(),
    ReactorSelectBigDecimalAsNumericTest<H2BigDecimalAsNumerics, BigDecimalAsNumericH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        BigDecimalAsNumericH2Repository(sqlClient)
}

class BigDecimalAsNumericH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorSelectBigDecimalAsNumericRepository<H2BigDecimalAsNumerics>(sqlClient, H2BigDecimalAsNumerics)
