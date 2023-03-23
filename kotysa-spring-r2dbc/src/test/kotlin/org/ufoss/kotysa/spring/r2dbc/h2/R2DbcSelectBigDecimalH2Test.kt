/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2BigDecimals
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalH2Test : AbstractR2dbcH2Test<BigDecimalH2Repository>(),
    ReactorSelectBigDecimalTest<H2BigDecimals, BigDecimalH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        BigDecimalH2Repository(sqlClient)
}

class BigDecimalH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorSelectBigDecimalRepository<H2BigDecimals>(sqlClient, H2BigDecimals)
