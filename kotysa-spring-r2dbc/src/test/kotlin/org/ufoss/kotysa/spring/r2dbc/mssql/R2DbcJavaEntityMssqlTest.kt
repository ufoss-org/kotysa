/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.r2dbc.mssql

import org.ufoss.kotysa.MssqlCoroutinesSqlClient
import org.ufoss.kotysa.MssqlReactorSqlClient
import org.ufoss.kotysa.spring.r2dbc.transaction.ReactorTransaction
import org.ufoss.kotysa.test.MssqlJavaUsers
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaEntityTest
import org.ufoss.kotysa.test.repositories.reactor.ReactorJavaUserRepository

class R2DbcJavaEntityMssqlTest :
    AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    ReactorJavaEntityTest<MssqlJavaUsers, JavaUserMssqlRepository, ReactorTransaction> {

    override fun instantiateRepository(sqlClient: MssqlReactorSqlClient, coSqlClient: MssqlCoroutinesSqlClient) =
        JavaUserMssqlRepository(sqlClient)
}


class JavaUserMssqlRepository(sqlClient: MssqlReactorSqlClient) :
    ReactorJavaUserRepository<MssqlJavaUsers>(sqlClient, MssqlJavaUsers)
