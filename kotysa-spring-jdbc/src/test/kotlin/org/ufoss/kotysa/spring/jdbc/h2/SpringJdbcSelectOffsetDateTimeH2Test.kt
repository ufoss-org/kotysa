/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2OffsetDateTimes
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectOffsetDateTimeTest

class SpringJdbcSelectOffsetDateTimeH2Test : AbstractSpringJdbcH2Test<OffsetDateTimeRepositoryH2Select>(),
    SelectOffsetDateTimeTest<H2OffsetDateTimes, OffsetDateTimeRepositoryH2Select, SpringJdbcTransaction> {
    override val context = startContext<OffsetDateTimeRepositoryH2Select>()
    override val repository = getContextRepository<OffsetDateTimeRepositoryH2Select>()
}

class OffsetDateTimeRepositoryH2Select(client: JdbcOperations) :
    SelectOffsetDateTimeRepository<H2OffsetDateTimes>(client.sqlClient(h2Tables), H2OffsetDateTimes)
