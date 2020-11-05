/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc

import org.springframework.beans.factory.getBean
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.transaction.coTransactionalOp
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.Repository

interface R2dbcRepositoryTest<T : Repository> {
    var repository: T
    var context: ConfigurableApplicationContext

    val operator get() = context.getBean<TransactionalOperator>().transactionalOp()
    val coOperator get() = context.getBean<TransactionalOperator>().coTransactionalOp()
}
