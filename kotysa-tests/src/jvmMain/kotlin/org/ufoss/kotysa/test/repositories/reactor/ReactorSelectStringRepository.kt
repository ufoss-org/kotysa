/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.reactor

import org.ufoss.kotysa.ReactorSqlClient
import org.ufoss.kotysa.test.Companies
import org.ufoss.kotysa.test.Roles
import org.ufoss.kotysa.test.UserRoles
import org.ufoss.kotysa.test.Users

abstract class ReactorSelectStringRepository<T : Roles, U : Users, V : UserRoles, W : Companies>(
    sqlClient: ReactorSqlClient,
    tableRoles: T,
    tableUsers: U,
    tableUserRoles: V,
    tableCompanies: W,
) : AbstractReactorUserRepository<T, U, V, W>(sqlClient, tableRoles, tableUsers, tableUserRoles, tableCompanies) {

    fun selectFirstByFirstnameNotNullable(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname eq firstname
                ).fetchFirst()

    fun selectAllByFirstnameNotEq(firstname: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname notEq firstname
                ).fetchAll()

    fun selectAllByFirstnameIn(firstnames: Sequence<String>) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname `in` firstnames
                ).fetchAll()

    fun selectByAlias(alias: String?) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias eq alias
                ).fetchAll()

    fun selectAllByAliasNotEq(alias: String?) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias notEq alias
                ).fetchAll()

    fun selectAllByFirstnameContains(firstnameContains: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname contains firstnameContains
                ).fetchAll()

    fun selectAllByFirstnameContainsIgnoreCase(firstnameContains: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname containsIgnoreCase firstnameContains
                ).fetchAll()

    fun selectAllByFirstnameStartsWith(firstnameStartsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname startsWith firstnameStartsWith
                ).fetchAll()

    fun selectAllByFirstnameStartsWithIgnoreCase(firstnameStartsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname startsWithIgnoreCase firstnameStartsWith
                ).fetchAll()

    fun selectAllByFirstnameEndsWith(firstnameEndsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname endsWith firstnameEndsWith
                ).fetchAll()

    fun selectAllByFirstnameEndsWithIgnoreCase(firstnameEndsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.firstname endsWithIgnoreCase firstnameEndsWith
                ).fetchAll()

    fun selectAllByAliasContains(aliasContains: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias contains aliasContains
                ).fetchAll()

    fun selectAllByAliasStartsWith(aliasStartsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias startsWith aliasStartsWith
                ).fetchAll()

    fun selectAllByAliasEndsWith(aliasEndsWith: String) =
        (sqlClient selectFrom tableUsers
                where tableUsers.alias endsWith aliasEndsWith
                ).fetchAll()
}
