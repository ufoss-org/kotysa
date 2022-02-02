/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository

class R2dbcJavaEntityH2Test :
    AbstractR2dbcH2Test<JavaUserH2Repository>(),
    CoroutinesJavaEntityTest<H2_JAVA_USER, JavaUserH2Repository, R2dbcTransaction> {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = JavaUserH2Repository(sqlClient)
}


class JavaUserH2Repository(sqlClient: R2dbcSqlClient) :
    CoroutinesJavaUserRepository<H2_JAVA_USER>(sqlClient, H2_JAVA_USER)
