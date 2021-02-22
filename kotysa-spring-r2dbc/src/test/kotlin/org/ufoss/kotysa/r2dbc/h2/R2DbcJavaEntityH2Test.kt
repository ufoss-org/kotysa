/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.springframework.beans.factory.getBean
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.transaction.reactive.TransactionalOperator
import org.ufoss.kotysa.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.transactionalOp
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables


class R2DbcJavaEntityH2Test :
        AbstractR2dbcH2Test<JavaUserH2Repository>(), R2dbcJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository> {
    override var context = startContext<JavaUserH2Repository>()
    override var repository = getContextRepository<JavaUserH2Repository>()
    override val operator = context.getBean<TransactionalOperator>().transactionalOp()
}


class JavaUserH2Repository(client: DatabaseClient)
    : R2dbcJavaUserRepository<H2_JAVA_USER>(client.sqlClient(h2Tables), H2_JAVA_USER)
