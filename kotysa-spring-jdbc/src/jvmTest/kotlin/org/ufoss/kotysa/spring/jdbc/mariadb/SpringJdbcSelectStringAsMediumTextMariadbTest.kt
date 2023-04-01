/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.mariadb

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.MariadbMediumTexts
import org.ufoss.kotysa.test.mariadbTables
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringAsMediumTextTest

class SpringJdbcSelectStringAsMediumTextMariadbTest :
    AbstractSpringJdbcMariadbTest<StringAsMediumTextRepositoryMariadbSelect>(),
    SelectStringAsMediumTextTest<MariadbMediumTexts, StringAsMediumTextRepositoryMariadbSelect, SpringJdbcTransaction> {

    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        StringAsMediumTextRepositoryMariadbSelect(jdbcOperations)
}

class StringAsMediumTextRepositoryMariadbSelect(client: JdbcOperations) :
    SelectStringAsMediumTextRepository<MariadbMediumTexts>(client.sqlClient(mariadbTables), MariadbMediumTexts)
