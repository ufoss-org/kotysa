/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbKotlinxLocalTimes
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectKotlinxLocalTimeTest

class SpringJdbcSelectKotlinxLocalTimeMariadbTest :
    AbstractSpringJdbcMariadbTest<KotlinxLocalTimeRepositoryMariadbSelect>(),
    SelectKotlinxLocalTimeTest<MariadbKotlinxLocalTimes, KotlinxLocalTimeRepositoryMariadbSelect,
            SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        KotlinxLocalTimeRepositoryMariadbSelect(jdbcOperations)
}

class KotlinxLocalTimeRepositoryMariadbSelect(client: JdbcOperations) :
    SelectKotlinxLocalTimeRepository<MariadbKotlinxLocalTimes>(
        client.sqlClient(mariadbTables),
        MariadbKotlinxLocalTimes
    )
