/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2_JAVA_USER
import org.ufoss.kotysa.test.repositories.JavaEntityTest
import org.ufoss.kotysa.test.repositories.JavaUserRepository

class JdbcJavaEntityH2Test :
        AbstractJdbcH2Test<JavaUserH2Repository>(), JavaEntityTest<H2_JAVA_USER, JavaUserH2Repository, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserH2Repository(sqlClient)
}


class JavaUserH2Repository(sqlClient: JdbcSqlClient)
    : JavaUserRepository<H2_JAVA_USER>(sqlClient, H2_JAVA_USER)
