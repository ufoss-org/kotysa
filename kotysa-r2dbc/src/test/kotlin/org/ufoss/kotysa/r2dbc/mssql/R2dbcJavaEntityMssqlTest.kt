/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.mssql

import io.r2dbc.spi.Connection
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.r2dbc.transaction.R2dbcTransaction
import org.ufoss.kotysa.test.MSSQL_JAVA_USER
import org.ufoss.kotysa.test.mssqlTables
import org.ufoss.kotysa.test.repositories.CoroutinesJavaEntityTest
import org.ufoss.kotysa.test.repositories.CoroutinesJavaUserRepository

class R2dbcJavaEntityMssqlTest :
    AbstractR2dbcMssqlTest<JavaUserMssqlRepository>(),
    CoroutinesJavaEntityTest<MSSQL_JAVA_USER, JavaUserMssqlRepository, R2dbcTransaction> {

    override fun instantiateRepository(connection: Connection) = JavaUserMssqlRepository(connection)
}


class JavaUserMssqlRepository(connection: Connection) :
    CoroutinesJavaUserRepository<MSSQL_JAVA_USER>(connection.sqlClient(mssqlTables), MSSQL_JAVA_USER)
