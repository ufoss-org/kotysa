/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.core.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.coroutines.CoroutinesJavaUserRepository

class R2dbcJavaEntityMssqlTest :
    AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    CoroutinesJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: R2dbcSqlClient) :
    CoroutinesJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
