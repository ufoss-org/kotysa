/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.mariadb

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class JdbcSelectLongMariadbTest : AbstractJdbcMariadbTest<SelectLongRepositoryMariadbSelect>(),
    SelectLongTest<MariadbLongs, SelectLongRepositoryMariadbSelect, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectLongRepositoryMariadbSelect(sqlClient)
}

class SelectLongRepositoryMariadbSelect(sqlClient: JdbcSqlClient) : SelectLongRepository<MariadbLongs>(sqlClient, MariadbLongs)
