/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Doubles
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectDoubleTest

class R2DbcSelectDoubleH2Test : AbstractR2dbcH2Test<DoubleH2Repository>(),
    ReactorSelectDoubleTest<H2Doubles, DoubleH2Repository, ReactorTransaction> {
    override val context = startContext<DoubleH2Repository>()
    override val repository = getContextRepository<DoubleH2Repository>()
}

class DoubleH2Repository(client: DatabaseClient) :
    ReactorSelectDoubleRepository<H2Doubles>(client.sqlClient(h2Tables), H2Doubles)
