/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc

import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import org.ufoss.kotysa.spring.jdbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository

interface SpringJdbcRepositoryTest<T : Repository> {
    var repository: T
    var context: ConfigurableApplicationContext

    private val transactionManager get() = context.getBean<PlatformTransactionManager>()
    val operator get() = TransactionTemplate(transactionManager).transactionalOp()
}
