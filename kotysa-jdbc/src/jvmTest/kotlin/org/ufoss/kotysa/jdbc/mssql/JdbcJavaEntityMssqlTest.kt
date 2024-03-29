/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.repositories.blocking.JavaEntityTest
import org.ufoss.kotysa.test.repositories.blocking.JavaUserRepository

class JdbcJavaEntityMssqlTest :
    AbstractJdbcMssqlTest<JavaUserMssqlRepository>(),
    JavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, JdbcTransaction> {

    override fun instantiateRepository(sqlClient: JdbcSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: JdbcSqlClient) : JavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
