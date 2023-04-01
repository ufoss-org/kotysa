/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.h2

import org.ufoss.kotysa.H2CoroutinesSqlClient
import org.ufoss.kotysa.H2ReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.H2JavaUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityH2Test :
    AbstractR2dbcH2Test<JavaUserH2Repository>(),
    ReactorJavaEntityTest<H2JavaUsers, JavaUserH2Repository, ReactorTransaction> {
    override fun instantiateRepository(sqlClient: H2ReactorSqlClient, coSqlClient: H2CoroutinesSqlClient) =
        JavaUserH2Repository(sqlClient)
}


class JavaUserH2Repository(sqlClient: H2ReactorSqlClient) :
    ReactorJavaUserRepository<H2JavaUsers>(sqlClient, H2JavaUsers)
