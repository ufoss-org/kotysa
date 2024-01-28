/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.spring.jdbc.h2

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcOperations
import org.ufoss.kotysa.spring.jdbc.sqlClient
import org.ufoss.kotysa.spring.jdbc.transaction.SpringJdbcTransaction
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.SelectRepository
import org.ufoss.kotysa.test.repositories.blocking.SelectTest

class SpringJdbcSelectH2Test : AbstractSpringJdbcH2Test<UserRepositorySpringJdbcH2Select>(),
    SelectTest<H2Roles, H2Users, H2UserRoles, H2Companies, UserRepositorySpringJdbcH2Select, SpringJdbcTransaction> {
    override fun instantiateRepository(jdbcOperations: JdbcOperations) =
        UserRepositorySpringJdbcH2Select(jdbcOperations)

    @Test
    override fun `Verify selectWithCascadeFullJoin works correctly`() {
        assertThatThrownBy { super.`Verify selectWithCascadeFullJoin works correctly`() }
            .isInstanceOf(BadSqlGrammarException::class.java)
    }
}

class UserRepositorySpringJdbcH2Select(client: JdbcOperations) :
    SelectRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        client.sqlClient(h2Tables),
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    )
