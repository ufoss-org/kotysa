/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2Floats
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatRepository
import org.ufoss.kotysa.test.repositories.reactor.ReactorSelectFloatTest

class R2DbcSelectFloatH2Test : AbstractR2dbcH2Test<FloatH2Repository>(),
    ReactorSelectFloatTest<H2Floats, FloatH2Repository, ReactorTransaction> {
    override val context = startContext<FloatH2Repository>()
    override val repository = getContextRepository<FloatH2Repository>()
}

class FloatH2Repository(client: DatabaseClient) :
    ReactorSelectFloatRepository<H2Floats>(client.sqlClient(h2Tables), H2Floats)
