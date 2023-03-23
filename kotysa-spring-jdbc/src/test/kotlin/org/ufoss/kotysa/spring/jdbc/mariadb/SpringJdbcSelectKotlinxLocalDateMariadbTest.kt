/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalDates
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalDateTest

class SpringJdbcSelectKotlinxLocalDateMariadbTest :
    AbstractSpringJdbcMariadbTest<KotlinxLocalDateRepositoryMariadbSelect>(),
    SelectKotlinxLocalDateTest<MariadbKotlinxLocalDates, KotlinxLocalDateRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalDateRepositoryMariadbSelect(jdbcOperations)
}

class KotlinxLocalDateRepositoryMariadbSelect(client: JdbcOperations) :
    SelectKotlinxLocalDateRepository<MariadbKotlinxLocalDates>(
        client.sqlClient(mariadbTables),
        MariadbKotlinxLocalDates
    )
