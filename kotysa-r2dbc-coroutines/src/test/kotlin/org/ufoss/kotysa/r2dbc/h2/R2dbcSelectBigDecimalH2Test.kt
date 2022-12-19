/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2BigDecimals
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesSelectBigDecimalTest

class R2dbcSelectBigDecimalH2Test : AbstractR2dbcH2Test<BigDecimalRepositoryH2Select>(),
    CoroutinesSelectBigDecimalTest<H2BigDecimals, BigDecimalRepositoryH2Select, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = BigDecimalRepositoryH2Select(sqlClient)
}

class BigDecimalRepositoryH2Select(sqlClient: R2dbcSqlClient) :
    CoroutinesSelectBigDecimalRepository<H2BigDecimals>(sqlClient, H2BigDecimals)
