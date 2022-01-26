/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.Connection
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository


class R2dbcJavaEntityH2Test :
        AbstractR2dbcH2Test<JavaUserH2Repository>(), CoroutinesJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository, R2dbcTransaction> {
    override fun instantiateRepository(connection: Connection) = JavaUserH2Repository(connection)
}


class JavaUserH2Repository(connection: Connection)
    : CoroutinesJavaUserRepository<H2_JAVA_USER>(connection.sqlClient(h2Tables), H2_JAVA_USER)
