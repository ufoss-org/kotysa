/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MssqlUuids
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectUuidTest

class JdbcSelectUuidMssqlTest : AbstractJdbcMssqlTest<UuidRepositoryMssqlSelect>(),
    SelectUuidTest<MssqlUuids, UuidRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) =
        UuidRepositoryMssqlSelect(sqlClient)
}

class UuidRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectUuidRepository<MssqlUuids>(sqlClient, MssqlUuids)
