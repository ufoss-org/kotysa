/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectBooleanTest

class SpringJdbcSelectBooleanH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2SelectBoolean>(),
    SelectBooleanTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositorySpringJdbcH2SelectBoolean,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcH2SelectBoolean(jdbcOperations)
}

class UserRepositorySpringJdbcH2SelectBoolean(client: JdbcOperations) :
    SelectBooleanRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        client.sqlClient(h2Tables),
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
