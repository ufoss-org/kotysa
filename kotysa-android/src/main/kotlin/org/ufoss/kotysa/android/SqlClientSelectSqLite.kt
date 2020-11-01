/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.android

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.ufoss.kotysa.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import kotlin.reflect.KClass

internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Select<T : Any> internal constructor(
        override val client: SQLiteDatabase,
        override val tables: Tables,
        override val resultClass: KClass<T>,
        override val dsl: (SelectDslApi.(ValueProvider) -> T)?
    ) : BlockingSqlClientSelect.Select<T>(), DefaultSqlClientSelect.Select<T>, Whereable<T>, Return<T> {

        override val properties: Properties<T> = initProperties()

        override fun <U : Any> join(
            joinClass: KClass<U>,
            alias: String?,
            type: JoinType
        ): BlockingSqlClientSelect.Joinable<T> =
            Joinable(client, properties, joinClass, alias, type)
    }

    private class Joinable<T : Any, U : Any>(
        private val client: SQLiteDatabase,
        private val properties: Properties<T>,
        private val joinClass: KClass<U>,
        private val alias: String?,
        private val type: JoinType
    ) : BlockingSqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): BlockingSqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, BlockingSqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, BlockingSqlClientSelect.Whereable<T> {
        val client: SQLiteDatabase

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }

    private class Where<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T>, BlockingSqlClientSelect.Where<T>, Return<T> {

        override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): BlockingSqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, BlockingSqlClientSelect.Return<T> {
        val client: SQLiteDatabase

        override fun fetchOne() = with(properties.selectInformation) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            if (!cursor.isLast) throw NonUniqueResultException()
            val row = SqLiteRow(cursor, fieldIndexMap)
            select(row, row)
        }

        override fun fetchOneOrNull() = with(properties.selectInformation) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                if (!cursor.isLast) throw NonUniqueResultException()
                val row = SqLiteRow(cursor, fieldIndexMap)
                select(row, row)
            }
        }

        override fun fetchFirst() = with(properties.selectInformation) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            val row = SqLiteRow(cursor, fieldIndexMap)
            select(row, row)
        }

        override fun fetchFirstOrNull() = with(properties.selectInformation) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                val row = SqLiteRow(cursor, fieldIndexMap)
                select(row, row)
            }
        }

        override fun fetchAll() = with(properties.selectInformation) {
            val cursor = fetch()
            val row = SqLiteRow(cursor, fieldIndexMap)
            val results = mutableListOf<T>()
            while (cursor.moveToNext()) {
                results.add(select(row, row))
            }
            results
        }

        private fun fetch() = client.rawQuery(selectSql(), buildWhereArgs())

        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        private class SqLiteRow(
            private val sqLiteCursor: Cursor,
            fieldIndexMap: Map<Field, Int>
        ) : AbstractRow(fieldIndexMap) {
            override fun <T> get(index: Int, type: Class<T>) =
                if (sqLiteCursor.isNull(index)) {
                    null
                } else {
                    when {
                        Integer::class.java.isAssignableFrom(type) -> sqLiteCursor.getInt(index)
                        java.lang.Long::class.java.isAssignableFrom(type) -> sqLiteCursor.getLong(index)
                        java.lang.Float::class.java.isAssignableFrom(type) -> sqLiteCursor.getFloat(index)
                        java.lang.Short::class.java.isAssignableFrom(type) -> sqLiteCursor.getShort(index)
                        java.lang.Double::class.java.isAssignableFrom(type) -> sqLiteCursor.getDouble(index)
                        String::class.java.isAssignableFrom(type) -> sqLiteCursor.getString(index)
                        // boolean is stored as Int
                        java.lang.Boolean::class.java.isAssignableFrom(type) -> sqLiteCursor.getInt(index) != 0
                        ByteArray::class.java.isAssignableFrom(type) -> sqLiteCursor.getBlob(index)
                        // Date are stored as String
                        LocalDate::class.java.isAssignableFrom(type) -> LocalDate.parse(sqLiteCursor.getString(index))
                        LocalDateTime::class.java.isAssignableFrom(type) -> LocalDateTime.parse(
                            sqLiteCursor.getString(index)
                        )
                        OffsetDateTime::class.java.isAssignableFrom(type) -> OffsetDateTime.parse(
                            sqLiteCursor.getString(index)
                        )
                        LocalTime::class.java.isAssignableFrom(type) -> LocalTime.parse(sqLiteCursor.getString(index))
                        else -> when (type.name) {
                            "kotlinx.datetime.LocalDate" -> kotlinx.datetime.LocalDate.parse(
                                    sqLiteCursor.getString(index)
                            )
                            "kotlinx.datetime.LocalDateTime" -> kotlinx.datetime.LocalDateTime.parse(
                                    sqLiteCursor.getString(index)
                            )
                            else -> throw UnsupportedOperationException(
                                    "${type.canonicalName} is not supported by Android SqLite"
                            )
                        }
                    } as T?
                }
        }
    }
}
