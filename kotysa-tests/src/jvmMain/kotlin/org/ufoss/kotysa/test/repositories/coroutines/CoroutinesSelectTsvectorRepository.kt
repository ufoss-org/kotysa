/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test.repositories.coroutines

import kotlinx.coroutines.runBlocking
import org.ufoss.kotysa.PostgresqlCoroutinesSqlClient
import org.ufoss.kotysa.QueryAlias
import org.ufoss.kotysa.postgresql.Tsquery
import org.ufoss.kotysa.test.PostgresqlTsvectors
import org.ufoss.kotysa.test.Repository
import org.ufoss.kotysa.test.tsVectorNotNull
import org.ufoss.kotysa.test.tsVectorNullable

abstract class CoroutinesSelectTsvectorRepository(private val sqlClient: PostgresqlCoroutinesSqlClient) : Repository {

    private val table = PostgresqlTsvectors

    override fun init() = runBlocking {
        createTables()
        insertTsvectors()
    }

    override fun delete() = runBlocking<Unit> {
        deleteAll()
    }

    private suspend fun createTables() {
        sqlClient createTableIfNotExists table
    }

    private suspend fun insertTsvectors() {
        sqlClient.insert(tsVectorNotNull, tsVectorNullable)
    }

    private suspend fun deleteAll() = sqlClient deleteAllFrom table

    fun selectToTsquerySingle(value: String) =
        (sqlClient selectFrom table
                where table.textSearchable toTsquery value
                ).fetchAll()

    fun selectToTsqueryBoth(value: String) =
        (sqlClient selectFrom table
                where table.textSearchableBoth toTsquery value
                ).fetchAll()

    fun selectPlaintoTsqueryBoth(value: String) =
        (sqlClient selectFrom table
                where table.textSearchableBoth plaintoTsquery value
                ).fetchAll()

    fun selectPhrasetoTsqueryBoth(value: String) =
        (sqlClient selectFrom table
                where table.textSearchableBoth phrasetoTsquery value
                ).fetchAll()

    fun websearchToTsqueryBoth(value: String) =
        (sqlClient selectFrom table
                where table.textSearchableBoth websearchToTsquery value
                ).fetchAll()

    // see https://www.postgresql.org/docs/15/textsearch-controls.html#TEXTSEARCH-RANKING
    fun selectRankedToTsqueryBoth(value: String) =
        selectWithTsquery(sqlClient toTsquery value `as` "query")

    fun selectRankedPlaintoTsqueryBoth(value: String) =
        selectWithTsquery(sqlClient plaintoTsquery value `as` "query")

    fun selectRankedPhrasetoTsqueryBoth(value: String) =
        selectWithTsquery(sqlClient phrasetoTsquery value `as` "query")

    fun selectRankedWebsearchToTsqueryBoth(value: String) =
        selectWithTsquery(sqlClient websearchToTsquery  value `as` "query")

    private fun selectWithTsquery(tsquery: Tsquery) =
        sqlClient.select(table.stringNotNull).andTsRankCd(table.textSearchableBoth, tsquery).`as`("rank")
            .from(table).and(tsquery)
            .where(tsquery).applyOn(table.textSearchableBoth)
            .orderByDesc(QueryAlias<Float>("rank"))
            .fetchAll()
}
