/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mssql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MssqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class VertxCoroutinesInheritanceMssqlTest : AbstractVertxCoroutinesMssqlTest<InheritanceMssqlRepository>(),
    CoroutinesInheritanceTest<MssqlInheriteds, InheritanceMssqlRepository, Transaction> {
    override val table = MssqlInheriteds
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = InheritanceMssqlRepository(sqlClient)
}

class InheritanceMssqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInheritanceRepository<MssqlInheriteds>(sqlClient, MssqlInheriteds)
