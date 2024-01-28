/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.r2dbc.h2

import io.r2dbc.spi.R2dbcBadGrammarException
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.R2dbcSqlClient
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.coroutines.AbstractCoroutinesUserRepository

class R2dbcSelectAliasH2Test : AbstractR2dbcH2Test<UserRepositorySelectAlias>() {
    override fun instantiateRepository(sqlClient: R2dbcSqlClient) = UserRepositorySelectAlias(sqlClient)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet throws R2dbcBadGrammarException`() {
        assertThatThrownBy {
            runTest {
                repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname)
            }
        }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias throws R2dbcBadGrammarException`() {
        assertThatThrownBy {
            runTest {
                repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname)
            }
        }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias throws R2dbcBadGrammarException`() {
        assertThatThrownBy {
            runTest {
                repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
            }
        }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameGet().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAlias().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() = runTest {
        assertThat(repository.selectCountUserGroupByCountryGet().toList())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() = runTest {
        assertThat(repository.selectCountUserGroupByCountryAlias().toList())
            .hasSize(2)
            .containsExactly(Pair(1, userJdoe.roleId), Pair(1, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias throws R2dbcBadGrammarException`() {
        assertThatThrownBy {
            runTest {
                repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)).toList()
            }
        }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() = runTest {
        assertThat(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws SQLiteException`() {
        assertThatThrownBy {
            runTest {
                repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname)
            }
        }
            .isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() = runTest {
        assertThat(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery returns results`() = runTest {
        assertThat(repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).toList())
            .hasSize(2)
            .containsExactlyInAnyOrder(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAliasSubQuery returns results`() = runTest {
        assertThat(repository.selectAliasedFirstnameOrderByFirstnameAliasSubQuery().toList())
            .hasSize(2)
            .containsExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAliasSubQuery returns User and Admin roles`() = runTest {
        assertThat(
            repository.selectRoleLabelWhereInUserSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)).toList()
        )
            .hasSize(2)
            .containsExactlyInAnyOrder(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectFirstnameByFirstnameTableAlias returns TheBoss firstname`() = runTest {
        assertThat(repository.selectFirstnameByFirstnameTableAlias(userBboss.firstname))
            .isEqualTo(userBboss.firstname)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdTableAlias returns Admin role for TheBoss`() = runTest {
        assertThat(repository.selectRoleLabelAndIdFromUserIdTableAlias(userBboss.id))
            .isEqualTo(Pair(roleAdmin.label, roleAdmin.id))
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias throws SQLiteException`() {
        assertThatThrownBy {
            runTest {
                repository.selectRoleLabelAndIdFromUserIdMissingTableAlias(userBboss.id)
            }
        }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        assertThatThrownBy {
            runTest {
                repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
            }
        }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        assertThatThrownBy {
            runTest {
                repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
            }
        }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        assertThatThrownBy {
            runTest {
                repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
            }
        }.isInstanceOf(R2dbcBadGrammarException::class.java)
    }
}

class UserRepositorySelectAlias(sqlClient: R2dbcSqlClient) :
    AbstractCoroutinesUserRepository<H2Roles, H2Users, H2UserRoles, H2Companies>(
        sqlClient,
        H2Roles,
        H2Users,
        H2UserRoles,
        H2Companies
    ) {

    suspend fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                where H2Users.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct H2Roles.label
                andCaseWhenExists {
            (this select H2Users.id
                    from H2Users
                    where H2Users.roleId eq H2Roles.id
                    and H2Users.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from H2Roles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select H2Users.firstname `as` "fn"
                from H2Users
                orderByAsc H2Users.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select H2Users.firstname `as` "fna"
                from H2Users
                orderByAsc QueryAlias<String>("fna")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount H2Users.id and H2Users.roleId `as` "roleId"
                from H2Users
                groupBy H2Users.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount H2Users.id and H2Users.roleId `as` "roleId"
                from H2Users
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select H2Roles.label and H2Roles.id `as` "roleId"
                from H2Roles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where H2Users.firstname["fna"] eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where H2Users.firstname eq firstname
                ).fetchOne()

    suspend fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct H2Roles.label
                    andCaseWhenExists {
                (this select H2Users.id
                        from H2Users
                        where H2Users.roleId eq H2Roles.id
                        and H2Users.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from H2Roles)
        } where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select H2Users.firstname `as` "fna"
                    from H2Users)
        } orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select H2Roles.label and H2Roles.id `as` "roleId"
                    from H2Roles)
        } where QueryAlias<Int>("roleId") `in`
                {
                    (this select H2Users.roleId
                            from H2Users
                            where H2Users.id `in` userIds)
                })
            .fetchAll()

    suspend fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select H2Users["u"].firstname
                from H2Users `as` "u"
                where H2Users["u"].firstname eq firstname
                ).fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles.id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles.id eq H2Users["u"].roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users.roleId
                where H2Users["u"].id eq userId)
            .fetchOne()

    suspend fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select H2Roles["r"].label and H2Roles["r"].id
                from H2Roles `as` "r" innerJoin H2Users `as` "u" on H2Roles["r"].id eq H2Users["u"].roleId
                where H2Users.id eq userId)
            .fetchOne()
}
