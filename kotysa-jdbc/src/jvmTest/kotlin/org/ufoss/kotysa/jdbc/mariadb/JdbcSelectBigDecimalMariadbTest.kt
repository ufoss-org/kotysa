/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.MariadbBigDecimals
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBigDecimalTest

class JdbcSelectBigDecimalMariadbTest : AbstractJdbcMariadbTest<SelectBigDecimalRepositoryMariadbSelect>(),
    SelectBigDecimalTest<MariadbBigDecimals, SelectBigDecimalRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectBigDecimalRepositoryMariadbSelect(sqlClient)
}

class SelectBigDecimalRepositoryMariadbSelect(sqlClient: JdbcSqlClient) :
    SelectBigDecimalRepository<MariadbBigDecimals>(sqlClient, MariadbBigDecimals)
