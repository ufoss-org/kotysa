/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mysql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceRepository
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesInheritanceTest

class R2dbcInheritanceMysqlTest : AbstractR2dbcMysqlTest<InheritanceMysqlRepository>(),
    CoroutinesInheritanceTest<MysqlInheriteds, InheritanceMysqlRepository, R2dbcTransaction> {
    override val table = MysqlInheriteds
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = InheritanceMysqlRepository(sqlClient)
}

class InheritanceMysqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesInheritanceRepository<MysqlInheriteds>(sqlClient, MysqlInheriteds)
