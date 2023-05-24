/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.junit.jupiter.api.Order
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectIntAsIdentitiesTest

@Order(1)
class JdbcSelectIntAsIdentitiesH2Test : AbstractJdbcH2Test<SelectIntAsIdentitiesRepositoryH2Select>(),
    SelectIntAsIdentitiesTest<H2IntAsIdentities, SelectIntAsIdentitiesRepositoryH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = SelectIntAsIdentitiesRepositoryH2Select(sqlClient)
}

class SelectIntAsIdentitiesRepositoryH2Select(sqlClient: JdbcSqlClient) :
    SelectIntAsIdentitiesRepository<H2IntAsIdentities>(sqlClient, H2IntAsIdentities)
