/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.oracle

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.OracleLocalDates
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLocalDateTest

class JdbcSelectLocalDateOracleTest : AbstractJdbcOracleTest<LocalDateRepositoryOracleSelect>(),
    SelectLocalDateTest<OracleLocalDates, LocalDateRepositoryOracleSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = LocalDateRepositoryOracleSelect(sqlClient)
}

class LocalDateRepositoryOracleSelect(sqlClient: JdbcSqlClient) :
    SelectLocalDateRepository<OracleLocalDates>(sqlClient, OracleLocalDates)
