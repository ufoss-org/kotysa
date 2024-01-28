/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class SpringJdbcUpdateDeleteH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2UpdateDelete>(),
    UpdateDeleteTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositorySpringJdbcH2UpdateDelete,
            SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcH2UpdateDelete(jdbcOperations)
}

class UserRepositorySpringJdbcH2UpdateDelete(client: JdbcOperations) :
    UpdateDeleteRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        client.sqlClient(h2Tables),
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
