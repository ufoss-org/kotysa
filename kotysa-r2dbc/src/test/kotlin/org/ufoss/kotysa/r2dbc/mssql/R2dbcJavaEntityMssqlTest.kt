/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.r2dbc.R2dbcSqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MSSQL_JAVA_USER
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository

class R2dbcJavaEntityMssqlTest :
    AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    CoroutinesJavaEntityTest<MSSQL_JAVA_USER, JavaUserMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesJavaUserRepository<MSSQL_JAVA_USER>(sqlClient, MSSQL_JAVA_USER)
