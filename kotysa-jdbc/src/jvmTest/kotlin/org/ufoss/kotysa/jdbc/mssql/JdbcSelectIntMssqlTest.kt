/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mssql

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class JdbcSelectIntMssqlTest : AbstractJdbcMssqlTest<SelectIntRepositoryMssqlSelect>(),
    SelectIntAsIdentitiesTest<MssqlIntAsIdentities, SelectIntRepositoryMssqlSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectIntRepositoryMssqlSelect(sqlClient)
}


class SelectIntRepositoryMssqlSelect(sqlClient: JdbcSqlClient) :
    SelectIntAsIdentitiesRepository<MssqlIntAsIdentities>(sqlClient, MssqlIntAsIdentities)
