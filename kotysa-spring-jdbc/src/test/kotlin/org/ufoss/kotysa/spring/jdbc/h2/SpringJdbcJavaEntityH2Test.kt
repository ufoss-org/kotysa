/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.beans.factory.getBean
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository


class SpringJdbcJavaEntityH2Test :
        AbstractSpringJdbcH2Test<JavaUserH2Repository>(), JavaEntityTest<H2_JAVA_USER, JavaUserH2Repository> {
    override var context = startContext<JavaUserH2Repository>()
    override var repository = getContextRepository<JavaUserH2Repository>()
    private val transactionManager = context.getBean<PlatformTransactionManager>()
    override val operator = TransactionTemplate(transactionManager).transactionalOp()
}


class JavaUserH2Repository(client: JdbcOperations)
    : JavaUserRepository<H2_JAVA_USER>(client.sqlClient(h2Tables), H2_JAVA_USER)
