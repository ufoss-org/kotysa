/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteRepository
import org.ufoss.kotysa.test.repositories.blocking.UpdateDeleteTest

class JdbcUpdateDeleteH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2UpdateDelete>(),
    UpdateDeleteTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2UpdateDelete, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2UpdateDelete(sqlClient)
}

class UserRepositoryJdbcH2UpdateDelete(sqlClient: JdbcSqlClient) :
    UpdateDeleteRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)
