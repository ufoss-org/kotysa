/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2JavaUsers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository


class SpringJdbcJavaEntityH2Test :
    AbstractSpringJdbcH2Test<JavaUserH2Repository>(),
    JavaEntityTest<H2JavaUsers, JavaUserH2Repository, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = JavaUserH2Repository(jdbcOperations)
}


class JavaUserH2Repository(client: JdbcOperations) :
    JavaUserRepository<H2JavaUsers>(client.sqlClient(h2Tables), H2JavaUsers)
