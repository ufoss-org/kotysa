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
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SpringJdbcSelectH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2Select>(),
    SelectTest<H2Roles, H2Users, H2UserRoles, UserRepositorySpringJdbcH2Select, SpringJdbcTransaction> {
    override val context = startContext<UserRepositorySpringJdbcH2Select>()
    override val repository = getContextRepository<UserRepositorySpringJdbcH2Select>()
}

class UserRepositorySpringJdbcH2Select(client: JdbcOperations) :
    SelectRepository<H2Roles, H2Users, H2UserRoles>(client.sqlClient(h2Tables), H2Roles, H2Users, H2UserRoles)
