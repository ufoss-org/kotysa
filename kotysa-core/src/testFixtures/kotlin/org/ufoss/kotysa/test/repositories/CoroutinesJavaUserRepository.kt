/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.CoroutinesSqlClient
import org.ufoss.kotysa.test.*

abstract class CoroutinesJavaUserRepository<T : JAVA_USER>(
        private val sqlClient: CoroutinesSqlClient,
        private val table: T,
) : Repository {

    override fun init() = runBlocking {
        createTable()
        insert()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTable() {
        sqlClient createTable table
    }

    suspend fun insert() {
        sqlClient.insert(javaJdoe, javaBboss)
    }

    suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectAll() = sqlClient selectAllFrom table

    suspend fun selectFirstByFirstname(firstname: String) =
            (sqlClient selectFrom table
                    where table.firstname eq firstname
                    ).fetchFirstOrNull()

    fun selectByAlias1(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias1 eq alias
                    ).fetchAll()

    fun selectByAlias2(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias2 eq alias
                    ).fetchAll()

    fun selectByAlias3(alias: String?) =
            (sqlClient selectFrom table
                    where table.alias3 eq alias
                    ).fetchAll()

    fun selectAllMappedToDto() =
            (sqlClient select { UserDto("${it[table.firstname]} ${it[table.lastname]}", it[table.alias1]) }
                    from table
                    ).fetchAll()
}
