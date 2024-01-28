/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.h2

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.JdbcSqlClient
import org.ufoss.kotysa.core.jdbc.transaction.JdbcTransaction
import org.ufoss.kotysa.test.H2Companies
import org.ufoss.kotysa.test.H2Roles
import org.ufoss.kotysa.test.H2UserRoles
import org.ufoss.kotysa.test.H2Users
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest
import java.sql.SQLSyntaxErrorException

class JdbcSelectH2Test : AbstractJdbcH2Test<UserRepositoryJdbcH2Select>(),
    SelectTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositoryJdbcH2Select, JdbcTransaction> {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcH2Select(sqlClient)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(SQLSyntaxErrorException::class.java)
    }
}

class UserRepositoryJdbcH2Select(sqlClient: JdbcSqlClient) :
    SelectRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(sqlClient, H2Roles, H2Users, H2UserRoles, H2Companies)
