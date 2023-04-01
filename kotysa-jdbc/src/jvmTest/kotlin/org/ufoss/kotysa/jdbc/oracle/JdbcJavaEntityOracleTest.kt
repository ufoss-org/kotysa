/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleJavaUsers
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository

class JdbcJavaEntityOracleTest :
    AbstractJdbcOracleTest<JavaUserOracleRepository>(),
    JavaEntityTest<OracleJavaUsers, JavaUserOracleRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserOracleRepository(sqlClient)
}


class JavaUserOracleRepository(sqlClient: JdbcSqlClient) : JavaUserRepository<OracleJavaUsers>(sqlClient, OracleJavaUsers)
