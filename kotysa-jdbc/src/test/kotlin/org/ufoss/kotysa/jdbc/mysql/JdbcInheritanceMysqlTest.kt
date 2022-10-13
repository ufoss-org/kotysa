/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mysql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class JdbcInheritanceMysqlTest : AbstractJdbcMysqlTest<InheritanceMysqlRepository>(),
    InheritanceTest<MysqlInheriteds, InheritanceMysqlRepository, JdbcTransaction> {
    override val table = MysqlInheriteds
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = InheritanceMysqlRepository(sqlClient)
}

class InheritanceMysqlRepository(sqlClient: JdbcSqlClient) :
    InheritanceRepository<MysqlInheriteds>(sqlClient, MysqlInheriteds)
