/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2JavaUsers
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityH2Test :
    AbstractSpringJdbcH2Test<JavaUserH2Repository>(),
    JavaEntityTest<H2JavaUsers, JavaUserH2Repository, SpringJdbcTransaction> {

    override val context = startContext<JavaUserH2Repository>()
    override val repository = getContextRepository<JavaUserH2Repository>()
}


class JavaUserH2Repository(client: JdbcOperations) :
    JavaUserRepository<H2JavaUsers>(client.sqlClient(h2Tables), H2JavaUsers)
