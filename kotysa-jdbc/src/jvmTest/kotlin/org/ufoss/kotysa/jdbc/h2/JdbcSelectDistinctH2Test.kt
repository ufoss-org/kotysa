/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectDistinctTest

class JdbcSelectDistinctH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2SelectDistinct>(),
    SelectDistinctTest<H2Roles, H2Users, H2UserRoles, UserRepositoryJdbcH2SelectDistinct, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2SelectDistinct(sqlClient)
}

class UserRepositoryJdbcH2SelectDistinct(sqlClient: JdbcSqlClient) :
    SelectDistinctRepository<H2Roles, H2Users, H2UserRoles>(sqlClient, H2Roles, H2Users, H2UserRoles)
