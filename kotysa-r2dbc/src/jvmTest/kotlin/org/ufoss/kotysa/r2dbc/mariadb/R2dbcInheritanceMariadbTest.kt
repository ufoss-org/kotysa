/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mariadb

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritanceMariadbTest : AbstractR2dbcMariadbTest<InheritanceMariadbRepository>(),
    CoroutinesInheritanceTest<MariadbInheriteds, InheritanceMariadbRepository, R2dbcTransaction> {
    override val table = MariadbInheriteds
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceMariadbRepository(sqlClient)
}

class InheritanceMariadbRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<MariadbInheriteds>(sqlClient, MariadbInheriteds)
