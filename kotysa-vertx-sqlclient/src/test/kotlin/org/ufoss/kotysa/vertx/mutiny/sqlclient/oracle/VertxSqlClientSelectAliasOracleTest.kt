/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.vertx.mutiny.sqlclient.oracle

import io.vertx.oracleclient.OracleException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.vertx.mutiny.sqlclient.VertxSqlClient
import org.ufoss.kotysa.vertx.mutiny.sqlclient.repositories.AbstractMutinyUserRepository

class VertxSqlClientSelectAliasOracleTest : AbstractVertxSqlClientOracleTest<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: VertxSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws JdbcOracleException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname).await().indefinitely()
        }
            .isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws JdbcOracleException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname).await().indefinitely()
        }
            .isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws JdbcOracleException`() {
        assertThatThrownBy {
            repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        }
            .isInstanceOf(OracleException::class.java)
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
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() {
        assertThat(repository.selectCountUserGroupByCountryAlias().await().indefinitely())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws JdbcOracleException`() {
        assertThatThrownBy {
            repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).await().indefinitely()
        }
            .isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname).await().indefinitely())
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias returns TheBoss firstname`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname).await()
                .indefinitely()
        }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws OracleException`() {
        assertThatThrownBy {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname).await()
                .indefinitely()
        }.isInstanceOf(OracleException::class.java)
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
        }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id).await().indefinitely()
        }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id).await().indefinitely()
        }.isInstanceOf(OracleException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id).await().indefinitely()
        }.isInstanceOf(OracleException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: VertxSqlClient) :
    AbstractMutinyUserRepository<OracleRoles, OracleUsers, OracleUserRoles>(
        sqlClient,
        OracleRoles,
        OracleUsers,
        OracleUserRoles
    ) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                where OracleUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct OracleRoles.label
                andCaseWhenExists {
            (this select OracleUsers.id
                    from OracleUsers
                    where OracleUsers.roleId eq OracleRoles.id
                    and OracleUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from OracleRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select OracleUsers.firstname `as` "fn"
                from OracleUsers
                orderByAsc OracleUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select OracleUsers.firstname `as` "fna"
                from OracleUsers
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount OracleUsers.id and OracleUsers.roleId `as` "roleId"
                from OracleUsers
                groupBy OracleUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount OracleUsers.id and OracleUsers.roleId `as` "roleId"
                from OracleUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select OracleRoles.label and OracleRoles.id `as` "roleId"
                from OracleRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select OracleUsers.roleId
                            from OracleUsers
                            where OracleUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where OracleUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where OracleUsers.firstname eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct OracleRoles.label
                    andCaseWhenExists {
                (this select OracleUsers.id
                        from OracleUsers
                        where OracleUsers.roleId eq OracleRoles.id
                        and OracleUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from OracleRoles)
        } `as` "dummy" where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select OracleUsers.firstname `as` "fna"
                    from OracleUsers)
        } `as` "dummy" orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select OracleRoles.label and OracleRoles.id `as` "roleId"
                    from OracleRoles)
        } `as` "dummy" where QueryAlias<Int>("roleId") `in`
                {
                    (this select OracleUsers.roleId
                            from OracleUsers
                            where OracleUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select OracleUsers["u"].firstname
                from OracleUsers `as` "u"
                where OracleUsers["u"].firstname eq firstname
                ).fetchOne()

    fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles.id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles.id eq OracleUsers["u"].roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers.roleId
                where OracleUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select OracleRoles["r"].label and OracleRoles["r"].id
                from OracleRoles `as` "r" innerJoin OracleUsers `as` "u" on OracleRoles["r"].id eq OracleUsers["u"].roleId
                where OracleUsers.id eq userId)
            .fetchOne()
}
