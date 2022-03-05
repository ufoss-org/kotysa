/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc.postgresql

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.jdbc.JdbcSqlClient
import org.ufoss.kotysa.test.*

class JdbcSubQueryPostgresqlTest : AbstractJdbcPostgresqlTest<UserRepositoryJdbcPostgresqlSubQuery>() {
    override fun instantiateRepository(sqlClient: JdbcSqlClient) = UserRepositoryJdbcPostgresqlSubQuery(sqlClient)

    @Test
    fun `Verify selectRoleLabelFromUserIdSubQuery returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelFromUserIdSubQuery(userBboss.id))
            .isEqualTo(Pair(userBboss.firstname, roleAdmin.label))
    }
}


class UserRepositoryJdbcPostgresqlSubQuery(private val sqlClient: JdbcSqlClient) : AbstractUserRepositoryJdbcPostgresql(sqlClient) {

    fun selectRoleLabelFromUserIdSubQuery(userId: Int) =
        (sqlClient select PostgresqlUsers.firstname and {
            (this select PostgresqlRoles.label
                    from PostgresqlRoles
                    where PostgresqlRoles.id eq PostgresqlUsers.roleId
                    and PostgresqlRoles.label eq roleAdmin.label)
        }
                from PostgresqlUsers
                where PostgresqlUsers.id eq userId)
            .fetchOne()
}
