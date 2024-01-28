/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.coroutines.mysql

import org.ufoss.kotysa.vertx.CoroutinesVertxSqlClient
import org.ufoss.kotysa.transaction.Transaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class VertxCoroutinesInheritanceMysqlTest : AbstractVertxCoroutinesMysqlTest<InheritanceMysqlRepository>(),
    CoroutinesInheritanceTest<MysqlInheriteds, InheritanceMysqlRepository, Transaction> {
    override val table = MysqlInheriteds
    override fun instantiateRepository(sqlClient: CoroutinesVertxSqlClient) = InheritanceMysqlRepository(sqlClient)
}

class InheritanceMysqlRepository(sqlClient: CoroutinesVertxSqlClient) :
    CoroutinesInheritanceRepository<MysqlInheriteds>(sqlClient, MysqlInheriteds)
