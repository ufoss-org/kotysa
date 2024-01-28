/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.expect
import org.junit.Test
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.get
import org.ufoss.kotysa.test.*
import org.ufoss.kotysa.test.repositories.blocking.AbstractUserRepository

class SqLiteSelectAliasTest : AbstractSqLiteTest<UserRepositorySelectAlias>() {

    override fun getRepository(sqLiteTables: SqLiteTables) = UserRepositorySelectAlias(dbHelper, sqLiteTables)

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGet returns TheBoss firstname`() {
        expect(repository.selectAliasedFirstnameByFirstnameGet(userBboss.firstname))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAlias returns TheBoss firstname`() {
        expect(repository.selectAliasedFirstnameByFirstnameAlias(userBboss.firstname))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAlias returns results`() {
        expect(repository.selectCaseWhenExistsSubQueryAlias(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameGet returns results`() {
        expect(repository.selectAliasedFirstnameOrderByFirstnameGet())
            .toHaveSize(2)
            .toContainExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAlias returns results`() {
        expect(repository.selectAliasedFirstnameOrderByFirstnameAlias())
            .toHaveSize(2)
            .toContainExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryGet counts and group`() {
        expect(repository.selectCountUserGroupByCountryGet())
            .toHaveSize(2)
            .toContainExactly(Pair(1L, userJdoe.roleId), Pair(1L, userBboss.roleId))
    }

    @Test
    fun `Verify selectCountCustomerGroupByCountryAlias counts and group`() {
        expect(repository.selectCountUserGroupByCountryAlias())
            .toHaveSize(2)
            .toContainExactly(Pair(1L, userJdoe.roleId), Pair(1L, userBboss.roleId))
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAlias returns User and Admin roles`() {
        expect(repository.selectRoleLabelWhereInUserSubQueryAlias(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQuery returns TheBoss firstname`() {
        expect(repository.selectAliasedFirstnameByFirstnameGetSubQuery(userBboss.firstname))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias throws SQLiteException`() {
        expect {
            repository.selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(userBboss.firstname)
        }.toThrow<SQLiteException>()
    }

    @Test
    fun `Verify selectAliasedFirstnameByFirstnameAliasSubQuery returns TheBoss firstname`() {
        expect(repository.selectAliasedFirstnameByFirstnameAliasSubQuery(userBboss.firstname))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectCaseWhenExistsSubQueryAliasSubQuery returns results`() {
        expect(repository.selectCaseWhenExistsSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(
                Pair(roleAdmin.label, true),
                Pair(roleUser.label, true),
            )
    }

    @Test
    fun `Verify selectAliasedFirstnameOrderByFirstnameAliasSubQuery returns results`() {
        expect(repository.selectAliasedFirstnameOrderByFirstnameAliasSubQuery())
            .toHaveSize(2)
            .toContainExactly(
                userBboss.firstname,
                userJdoe.firstname,
            )
    }

    @Test
    fun `Verify selectRoleLabelWhereInUserSubQueryAliasSubQuery returns User and Admin roles`() {
        expect(repository.selectRoleLabelWhereInUserSubQueryAliasSubQuery(listOf(userBboss.id, userJdoe.id)))
            .toHaveSize(2)
            .toContain(Pair(roleAdmin.label, roleAdmin.id), Pair(roleUser.label, roleUser.id))
    }

    @Test
    fun `Verify selectFirstnameByFirstnameTableAlias returns TheBoss firstname`() {
        expect(repository.selectFirstnameByFirstnameTableAlias(userBboss.firstname))
            .toEqual(userBboss.firstname)
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdTableAlias returns Admin role for TheBoss`() {
        expect(repository.selectRoleLabelAndIdFromUserIdTableAlias(userBboss.id))
            .toEqual(Pair(roleAdmin.label, roleAdmin.id))
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias throws SQLiteException`() {
        expect {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias(userBboss.id)
        }.toThrow<SQLiteException>()
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias2 throws SQLiteException`() {
        expect {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias2(userBboss.id)
        }.toThrow<SQLiteException>()
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias3 throws SQLiteException`() {
        expect {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias3(userBboss.id)
        }.toThrow<SQLiteException>()
    }

    @Test
    fun `Verify selectRoleLabelAndIdFromUserIdMissingTableAlias4 throws SQLiteException`() {
        expect {
            repository.selectRoleLabelAndIdFromUserIdMissingTableAlias4(userBboss.id)
        }.toThrow<SQLiteException>()
    }
}

class UserRepositorySelectAlias(
    sqLiteOpenHelper: SQLiteOpenHelper,
    tables: SqLiteTables,
) : AbstractUserRepository<SqliteRoles, SqliteUsers, SqliteUserRoles, SqliteCompanies>(
    sqLiteOpenHelper.sqlClient(tables),
    SqliteRoles,
    SqliteUsers,
    SqliteUserRoles,
    SqliteCompanies
) {

    fun selectAliasedFirstnameByFirstnameGet(firstname: String) =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                where SqliteUsers.firstname["fn"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAlias(firstname: String) =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                where QueryAlias<String>("fn") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAlias(userIds: List<Int>) =
        (sqlClient selectDistinct SqliteRoles.label
                andCaseWhenExists {
            (this select SqliteUsers.id
                    from SqliteUsers
                    where SqliteUsers.roleId eq SqliteRoles.id
                    and SqliteUsers.id `in` userIds)
        } then true `else` false `as` "roleUsedByUser"
                from SqliteRoles
                where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameGet() =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                orderByAsc SqliteUsers.firstname["fn"]
                ).fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAlias() =
        (sqlClient select SqliteUsers.firstname `as` "fn"
                from SqliteUsers
                orderByAsc QueryAlias<String>("fn")
                ).fetchAll()

    fun selectCountUserGroupByCountryGet() =
        (sqlClient selectCount SqliteUsers.id and SqliteUsers.roleId `as` "roleId"
                from SqliteUsers
                groupBy SqliteUsers.roleId["roleId"]
                ).fetchAll()

    fun selectCountUserGroupByCountryAlias() =
        (sqlClient selectCount SqliteUsers.id and SqliteUsers.roleId `as` "roleId"
                from SqliteUsers
                groupBy QueryAlias<Int>("roleId")
                ).fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAlias(userIds: List<Int>) =
        (sqlClient select SqliteRoles.label and SqliteRoles.id `as` "roleId"
                from SqliteRoles
                where QueryAlias<Int>("roleId") `in`
                {
                    (this select SqliteUsers.roleId
                            from SqliteUsers
                            where SqliteUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectAliasedFirstnameByFirstnameGetSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select SqliteUsers.firstname `as` "fna"
                    from SqliteUsers)
        } where SqliteUsers.firstname["fna"] eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameGetSubQueryMissingAlias(firstname: String) =
        (sqlClient selectStarFrom {
            (this select SqliteUsers.firstname `as` "fna"
                    from SqliteUsers)
        } where SqliteUsers.firstname eq firstname
                ).fetchOne()

    fun selectAliasedFirstnameByFirstnameAliasSubQuery(firstname: String) =
        (sqlClient selectStarFrom {
            (this select SqliteUsers.firstname `as` "fna"
                    from SqliteUsers)
        } where QueryAlias<String>("fna") eq firstname
                ).fetchOne()

    fun selectCaseWhenExistsSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this selectDistinct SqliteRoles.label
                    andCaseWhenExists {
                (this select SqliteUsers.id
                        from SqliteUsers
                        where SqliteUsers.roleId eq SqliteRoles.id
                        and SqliteUsers.id `in` userIds)
            } then true `else` false `as` "roleUsedByUser"
                    from SqliteRoles)
        } where QueryAlias<Boolean>("roleUsedByUser") eq true)
            .fetchAll()

    fun selectAliasedFirstnameOrderByFirstnameAliasSubQuery() =
        (sqlClient selectStarFrom {
            (this select SqliteUsers.firstname `as` "fna"
                    from SqliteUsers)
        } orderByAsc QueryAlias<String>("fna"))
            .fetchAll()

    fun selectRoleLabelWhereInUserSubQueryAliasSubQuery(userIds: List<Int>) =
        (sqlClient selectStarFrom {
            (this select SqliteRoles.label and SqliteRoles.id `as` "roleId"
                    from SqliteRoles)
        } where QueryAlias<Int>("roleId") `in`
                {
                    (this select SqliteUsers.roleId
                            from SqliteUsers
                            where SqliteUsers.id `in` userIds)
                })
            .fetchAll()

    fun selectFirstnameByFirstnameTableAlias(firstname: String) =
        (sqlClient select SqliteUsers["u"].firstname
                from SqliteUsers `as` "u"
                where SqliteUsers["u"].firstname eq firstname
                ).fetchOne()

    fun selectRoleLabelAndIdFromUserIdTableAlias(userId: Int) =
        (sqlClient select SqliteRoles["r"].label and SqliteRoles["r"].id
                from SqliteRoles `as` "r" innerJoin SqliteUsers `as` "u" on SqliteRoles["r"].id eq SqliteUsers["u"].roleId
                where SqliteUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias(userId: Int) =
        (sqlClient select SqliteRoles["r"].label and SqliteRoles.id
                from SqliteRoles `as` "r" innerJoin SqliteUsers `as` "u" on SqliteRoles["r"].id eq SqliteUsers["u"].roleId
                where SqliteUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias2(userId: Int) =
        (sqlClient select SqliteRoles["r"].label and SqliteRoles["r"].id
                from SqliteRoles `as` "r" innerJoin SqliteUsers `as` "u" on SqliteRoles.id eq SqliteUsers["u"].roleId
                where SqliteUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias3(userId: Int) =
        (sqlClient select SqliteRoles["r"].label and SqliteRoles["r"].id
                from SqliteRoles `as` "r" innerJoin SqliteUsers `as` "u" on SqliteRoles["r"].id eq SqliteUsers.roleId
                where SqliteUsers["u"].id eq userId)
            .fetchOne()

    fun selectRoleLabelAndIdFromUserIdMissingTableAlias4(userId: Int) =
        (sqlClient select SqliteRoles["r"].label and SqliteRoles["r"].id
                from SqliteRoles `as` "r" innerJoin SqliteUsers `as` "u" on SqliteRoles["r"].id eq SqliteUsers["u"].roleId
                where SqliteUsers.id eq userId)
            .fetchOne()
}
