/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectStringRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectStringTest

class SpringJdbcSelectStringH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectString>(),
    SelectStringTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositorySpringJdbcH2SelectString,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcH2SelectString(jdbcOperations)
}

class UserRepositorySpringJdbcH2SelectString(client: JdbcOperations) :
    SelectStringRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        client.sqlClient(h2Tables),
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
