/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.springframework.r2dbc.core.DatabaseClient
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaEntityTest
import org.ufoss.kotysa.spring.r2dbc.R2dbcJavaUserRepository
import org.ufoss.kotysa.spring.r2dbc.sqlClient
import org.ufoss.kotysa.test.H2JavaUsers
import org.ufoss.kotysa.test.h2Tables


class R2DbcJavaEntityH2Test :
        AbstractR2dbcH2Test<JavaUserH2Repository>(), R2dbcJavaEntityTest<H2JavaUsers, JavaUserH2Repository> {
    override val context = startContext<JavaUserH2Repository>()
    override val repository = getContextRepository<JavaUserH2Repository>()
}


class JavaUserH2Repository(client: DatabaseClient)
    : R2dbcJavaUserRepository<H2JavaUsers>(client.sqlClient(h2Tables), H2JavaUsers)
