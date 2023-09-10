/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mariadb

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MariadbInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class VertxCoroutinesInheritanceMariadbTest : AbstractVertxCoroutinesMariadbTest<InheritanceMariadbRepository>(),
    CoroutinesInheritanceTest<MariadbInheriteds, InheritanceMariadbRepository, Transaction> {
    override val table = MariadbInheriteds
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = InheritanceMariadbRepository(sqlClient)
}

class InheritanceMariadbRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInheritanceRepository<MariadbInheriteds>(sqlClient, MariadbInheriteds)
