/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mysql

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MysqlInheriteds
import org.ufoss.kotysa.test.mysqlTables
import org.ufoss.kotysa.test.repositories.blocking.InheritanceRepository
import org.ufoss.kotysa.test.repositories.blocking.InheritanceTest

class SpringJdbcInheritanceMysqlTest : AbstractSpringJdbcMysqlTest<InheritanceMysqlRepository>(),
    InheritanceTest<MysqlInheriteds, InheritanceMysqlRepository, SpringJdbcTransaction> {
    override val table = MysqlInheriteds

    override fun instantiateRepository(jdbcOperations: JdbcOperations) = InheritanceMysqlRepository(jdbcOperations)
}

class InheritanceMysqlRepository(client: JdbcOperations) :
    InheritanceRepository<MysqlInheriteds>(client.sqlClient(mysqlTables), MysqlInheriteds)
