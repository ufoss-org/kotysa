/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SubQueryRepository
import org.ufoss.kotysa.test.repositories.blocking.SubQueryTest

class SpringJdbcSubQueryH2Test : AbstractSpringJdbcH2Test<UserRepositoryJdbcH2SubQuery>(),
    SubQueryTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryJdbcH2SubQuery, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) = UserRepositoryJdbcH2SubQuery(jdbcOperations)
}

class UserRepositoryJdbcH2SubQuery(client: JdbcOperations) :
    SubQueryRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        client.sqlClient(h2Tables),
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
