/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2BigDecimals
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectBigDecimalTest

class R2DbcSelectBigDecimalH2Test : AbstractR2dbcH2Test<BigDecimalH2Repository>(),
    ReactorSelectBigDecimalTest<H2BigDecimals, BigDecimalH2Repository, ReactorTransaction> {
    override val context = startContext<BigDecimalH2Repository>()
    override val repository = getContextRepository<BigDecimalH2Repository>()
}

class BigDecimalH2Repository(client: DatabaseClient) :
    ReactorSelectBigDecimalRepository<H2BigDecimals>(client.sqlClient(h2Tables), H2BigDecimals)
