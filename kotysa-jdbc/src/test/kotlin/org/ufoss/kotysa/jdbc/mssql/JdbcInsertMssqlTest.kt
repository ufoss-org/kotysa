/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import com.microsoft.sqlserver.jdbc.SQLServerException
import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.InsertRepository
import org.ufoss.kotysa.test.repositories.blocking.InsertTest

@Order(3)
class JdbcInsertMssqlTest : AbstractJdbcMssqlTest<RepositoryMssqlInsert>(),
    InsertTest<MssqlInts, MssqlLongs, MssqlCustomers, RepositoryMssqlInsert, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = RepositoryMssqlInsert(sqlClient)
    override val exceptionClass = SQLServerException::class.java
}

class RepositoryMssqlInsert(sqlClient: JdbcSqlClient) :
    InsertRepository<MssqlInts, MssqlLongs, MssqlCustomers>(sqlClient, MssqlInts, MssqlLongs, MssqlCustomers)
