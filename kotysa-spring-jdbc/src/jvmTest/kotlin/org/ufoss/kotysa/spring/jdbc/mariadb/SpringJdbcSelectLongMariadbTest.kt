/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.junit.jupiter.api.Order
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectLongRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectLongTest

@Order(2)
class SpringJdbcSelectLongMariadbTest : AbstractSpringJdbcMariadbTest<SelectLongRepositoryMariadbSelect>(),
    SelectLongTest<MariadbLongs, SelectLongRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        SelectLongRepositoryMariadbSelect(jdbcOperations)
}

class SelectLongRepositoryMariadbSelect(client: JdbcOperations) :
    SelectLongRepository<MariadbLongs>(client.sqlClient(mariadbTables), MariadbLongs)
