/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.h2Tables
import org.ufoss.kotysa.test.repositories.blocking.SelectAndRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectAndTest

class SpringJdbcSelectAndH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectAnd>(),
    SelectAndTest<H2Roles, H2Users, H2UserRoles, UserRepositorySpringJdbcH2SelectAnd,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcH2SelectAnd(jdbcOperations)
}

class UserRepositorySpringJdbcH2SelectAnd(client: JdbcOperations) :
    SelectAndRepository<H2Roles, H2Users, H2UserRoles>(
        client.sqlClient(h2Tables),
        H2Roles, H2Users, H2UserRoles
    )
