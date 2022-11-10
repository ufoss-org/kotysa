/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.postgresql

import io.vertx.pgclient.PgException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.PostgresqlVertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient

class VertxSqlClientSelectAliasPostgresqlTest : AbstractVertxSqlClientPostgresqlTest<UserRepositorySelectAlias>() {

    override fun instantiateRepository(sqlClient: PostgresqlVertxSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcBadSqlGrammarException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname).await().indefinitely()
        }
            .isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcBadSqlGrammarException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname).await().indefinitely()
        }
            .isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcBadSqlGrammarException`() {
        assertThatThrownBy {
            repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        }
            .isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameGet().await().indefinitely())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAlias().await().indefinitely())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryGet().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryAlias().await().indefinitely())
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcBadSqlGrammarException`() {
        assertThatThrownBy {
            repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        }
            .isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname).await().indefinitely())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname).await()
                .indefinitely()
        }.isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() {
        assertThat(
            repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname).await().indefinitely()
        )
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery returns results`() {
        assertThat(
            repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).await()
                .indefinitely()
        )
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAliasSubQuery returns results`() {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAliasSubQuery().await().indefinitely())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAliasSubQuery returns User and Admin roles`() {
        assertThat(
            repository.selectRoleLabelWhereInUserSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).await()
                .indefinitely()
        )
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectFirstnameByFirstnameTableAlias returns TheBoss firstname`() {
        assertThat(repository.selectFirstnameByFirstnameTableAlias(userBboss.firstname).await().indefinitely())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdTableAlias returns Admin role for TheBoss`() {
        assertThat(repository.selectRoleLabelAndIdFromUserIdTableAlias(userBboss.id).await().indefinitely())
            .isEqualTo(Pair(roleAdmin.label, roleAdmin.id))
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias(userBboss.id).await().indefinitely()
        }.isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id).await().indefinitely()
        }.isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id).await().indefinitely()
        }.isInstanceOf(PgException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id).await().indefinitely()
        }.isInstanceOf(PgException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: VertxSqlClient) : AbstractUserRepositoryPostgresql(sqlClient) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select PostgresqlUsers.firstname `as` "fna"
                from PostgresqlUsers
                where PostgresqlUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select PostgresqlUsers.firstname `as` "fna"
                from PostgresqlUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct PostgresqlRoles.label
                andCaseWhenExists {
            (this select PostgresqlUsers.id
                    from PostgresqlUsers
                    where PostgresqlUsers.roleId eq PostgresqlRoles.id
                    and PostgresqlUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from PostgresqlRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select PostgresqlUsers.firstname `as` "fn"
                from PostgresqlUsers
                orderByAsc PostgresqlUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select PostgresqlUsers.firstname `as` "fna"
                from PostgresqlUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount PostgresqlUsers.id and PostgresqlUsers.roleId `as` "roleId"
                from PostgresqlUsers
                groupBy PostgresqlUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount PostgresqlUsers.id and PostgresqlUsers.roleId `as` "roleId"
                from PostgresqlUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select PostgresqlRoles.label and PostgresqlRoles.id `as` "roleId"
                from PostgresqlRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select PostgresqlUsers.roleId
                            from PostgresqlUsers
                            where PostgresqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select PostgresqlUsers.firstname `as` "fna"
                    from PostgresqlUsers)
        } `as` "derivedTable" where PostgresqlUsers.firstname["derivedTable.fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select PostgresqlUsers.firstname `as` "fna"
                    from PostgresqlUsers)
        } `as` "derivedTable" where PostgresqlUsers.firstname eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select PostgresqlUsers.firstname `as` "fna"
                    from PostgresqlUsers)
        } `as` "derivedTable" where QueryAlias<String>("derivedTable.fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct PostgresqlRoles.label
                    andCaseWhenExists {
                (this select PostgresqlUsers.id
                        from PostgresqlUsers
                        where PostgresqlUsers.roleId eq PostgresqlRoles.id
                        and PostgresqlUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from PostgresqlRoles)
        } `as` "derivedTable" where QueryAlias<Boolean>("derivedTable.roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select PostgresqlUsers.firstname `as` "fna"
                    from PostgresqlUsers)
        } `as` "derivedTable" orderByAsc QueryAlias<String>("derivedTable.fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select PostgresqlRoles.label and PostgresqlRoles.id `as` "roleId"
                    from PostgresqlRoles)
        } `as` "derivedTable" where QueryAlias<Int>("derivedTable.roleId") `in`
                {
                    (this select PostgresqlUsers.roleId
                            from PostgresqlUsers
                            where PostgresqlUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select PostgresqlUsers["u"].firstname
                from PostgresqlUsers `as` "u"
                where PostgresqlUsers["u"].firstname eq firstname
                ).fetchOne()

    fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select PostgresqlRoles["r"].label and PostgresqlRoles["r"].id
                from PostgresqlRoles `as` "r" innerJoin PostgresqlUsers `as` "u" on PostgresqlRoles["r"].id eq PostgresqlUsers["u"].roleId
                where PostgresqlUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select PostgresqlRoles["r"].label and PostgresqlRoles.id
                from PostgresqlRoles `as` "r" innerJoin PostgresqlUsers `as` "u" on PostgresqlRoles["r"].id eq PostgresqlUsers["u"].roleId
                where PostgresqlUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select PostgresqlRoles["r"].label and PostgresqlRoles["r"].id
                from PostgresqlRoles `as` "r" innerJoin PostgresqlUsers `as` "u" on PostgresqlRoles.id eq PostgresqlUsers["u"].roleId
                where PostgresqlUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select PostgresqlRoles["r"].label and PostgresqlRoles["r"].id
                from PostgresqlRoles `as` "r" innerJoin PostgresqlUsers `as` "u" on PostgresqlRoles["r"].id eq PostgresqlUsers.roleId
                where PostgresqlUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select PostgresqlRoles["r"].label and PostgresqlRoles["r"].id
                from PostgresqlRoles `as` "r" innerJoin PostgresqlUsers `as` "u" on PostgresqlRoles["r"].id eq PostgresqlUsers["u"].roleId
                where PostgresqlUsers.id eq userId)
            .fetchOne()
}
