/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class JdbcSelectLongMssqlTest : AbstractJdbcMssqlTest<SelectLongRepositoryMssqlSelect>(),
    SelectLongTest<MssqlLongs, SelectLongRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectLongRepositoryMssqlSelect(sqlClient)
}

class SelectLongRepositoryMssqlSelect(sqlClient: JdbcSqlClient) : SelectLongRepository<MssqlLongs>(sqlClient, MssqlLongs)
