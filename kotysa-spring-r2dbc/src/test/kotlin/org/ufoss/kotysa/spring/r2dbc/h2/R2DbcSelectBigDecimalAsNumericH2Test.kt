/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2BigDecimalAsNumerics
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalAsNumericTest

class R2DbcSelectBigDecimalAsNumericH2Test : AbstractR2dbcH2Test<BigDecimalAsNumericH2Repository>(),
    ReactorSelectBigDecimalAsNumericTest<H2BigDecimalAsNumerics, BigDecimalAsNumericH2Repository, ReactorTransaction> {
    override val context = startContext<BigDecimalAsNumericH2Repository>()
    override val repository = getContextRepository<BigDecimalAsNumericH2Repository>()
}

class BigDecimalAsNumericH2Repository(client: DatabaseClient) :
    ReactorSelectBigDecimalAsNumericRepository<H2BigDecimalAsNumerics>(client.sqlClient(h2Tables), H2BigDecimalAsNumerics)
