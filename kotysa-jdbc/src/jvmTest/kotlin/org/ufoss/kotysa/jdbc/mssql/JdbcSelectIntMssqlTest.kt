/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntTest

@Order(1)
class JdbcSelectIntMssqlTest : AbstractJdbcMssqlTest<SelectIntRepositoryMssqlSelect>(),
    SelectIntTest<MssqlInts, SelectIntRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectIntRepositoryMssqlSelect(sqlClient)
}


class SelectIntRepositoryMssqlSelect(sqlClient: JdbcSqlClient) : SelectIntRepository<MssqlInts>(sqlClient, MssqlInts)
