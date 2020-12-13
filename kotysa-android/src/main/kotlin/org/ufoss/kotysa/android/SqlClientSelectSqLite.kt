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

internal class SqlClientSelectSqLite private constructor() : DefaultSqlClientSelect() {

    internal class Selectable internal constructor(
            client: SQLiteDatabase,
            tables: Tables,
    ) : DefaultSqlClientSelect.Selectable<SqlClientSelect.Select, SqlClientSelect.From<Any>>(tables), SqlClientSelect.Selectable {
        override val select: DefaultSqlClientSelect.Select<SqlClientSelect.Select, SqlClientSelect.From<Any>> =
                Select(client, properties)
    }

    internal class Select internal constructor(
            client: SQLiteDatabase,
            properties: Properties<Any>,
    ) : DefaultSqlClientSelect.Select<SqlClientSelect.Select, SqlClientSelect.From<Any>>(properties), SqlClientSelect.Select {
        override val from: DefaultSqlClientSelect.From<*, SqlClientSelect.From<Any>, *> = From(client, properties)
        override val select: SqlClientSelect.Select = this
    }

    internal class From<T : Any> internal constructor(
            override val client: SQLiteDatabase,
            properties: Properties<T>,
    ) : DefaultSqlClientSelect.From<T, SqlClientSelect.From<T>, SqlClientSelect.Where<T>>(properties), SqlClientSelect.From<T>, Return<T> {
        override val where = Where(client, properties)
        override val from: SqlClientSelect.From<T> = this

        /*override fun <U : Any> join(
                joinClass: KClass<U>,
                alias: String?,
                type: JoinType
            ): SqlClientSelect.Joinable<T> =
                Joinable(client, properties, joinClass, alias, type)*/
    }

    /*
    private class Joinable<T : Any, U : Any>(
        private val client: SQLiteDatabase,
        private val properties: Properties<T>,
        private val joinClass: KClass<U>,
        private val alias: String?,
        private val type: JoinType
    ) : SqlClientSelect.Joinable<T> {

        override fun on(dsl: (FieldProvider) -> ColumnField<*, *>): SqlClientSelect.Join<T> {
            val join = Join(client, properties)
            join.addJoinClause(dsl, joinClass, alias, type)
            return join
        }
    }

    private class Join<T : Any>(
        override val client: SQLiteDatabase,
        override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Join<T>, SqlClientSelect.Join<T>, Whereable<T>, Return<T>

    private interface Whereable<T : Any> : DefaultSqlClientSelect.Whereable<T>, SqlClientSelect.Whereable<T> {
        val client: SQLiteDatabase

        override fun where(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            val where = Where(client, properties)
            where.addWhereClause(dsl)
            return where
        }
    }
     */

    internal class Where<T : Any>(
            override val client: SQLiteDatabase,
            override val properties: Properties<T>
    ) : DefaultSqlClientSelect.Where<T, SqlClientSelect.Where<T>>(), SqlClientSelect.Where<T>, Return<T> {
        override val where = this

        /*override fun and(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            addAndClause(dsl)
            return this
        }

        override fun or(dsl: WhereDsl.(FieldProvider) -> WhereClause): SqlClientSelect.Where<T> {
            addOrClause(dsl)
            return this
        }*/
    }

    private interface Return<T : Any> : DefaultSqlClientSelect.Return<T>, SqlClientSelect.Return<T> {
        val client: SQLiteDatabase

        override fun fetchOne() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            if (!cursor.isLast) throw NonUniqueResultException()
            val row = SqLiteRow(cursor)
            select(row)
        }

        override fun fetchOneOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                if (!cursor.isLast) throw NonUniqueResultException()
                val row = SqLiteRow(cursor)
                select(row)
            }
        }

        override fun fetchFirst() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) throw NoResultException()
            val row = SqLiteRow(cursor)
            select(row)
        }

        override fun fetchFirstOrNull() = with(properties) {
            val cursor = fetch()
            if (!cursor.moveToFirst()) {
                null
            } else {
                val row = SqLiteRow(cursor)
                select(row)
            }
        }

        override fun fetchAll() = with(properties) {
            val cursor = fetch()
            val row = SqLiteRow(cursor)
            val results = mutableListOf<T>()
            while (cursor.moveToNext()) {
                results.add(select(row))
                row.resetIndex()
            }
            results
        }

        private fun fetch() = client.rawQuery(selectSql(), buildWhereArgs())

        @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
        private class SqLiteRow(
                private val sqLiteCursor: Cursor
        ) : Row() {
            override fun <T : Any> get(index: Int, clazz: Class<T>) =
                    if (sqLiteCursor.isNull(index)) {
                        null
                    } else {
                        when {
                            Integer::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getInt(index)
                            java.lang.Long::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getLong(index)
                            java.lang.Float::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getFloat(index)
                            java.lang.Short::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getShort(index)
                            java.lang.Double::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getDouble(index)
                            String::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getString(index)
                            // boolean is stored as Int
                            java.lang.Boolean::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getInt(index) != 0
                            ByteArray::class.java.isAssignableFrom(clazz) -> sqLiteCursor.getBlob(index)
                            // Date are stored as String
                            LocalDate::class.java.isAssignableFrom(clazz) -> LocalDate.parse(sqLiteCursor.getString(index))
                            LocalDateTime::class.java.isAssignableFrom(clazz) -> LocalDateTime.parse(
                                    sqLiteCursor.getString(index)
                            )
                            OffsetDateTime::class.java.isAssignableFrom(clazz) -> OffsetDateTime.parse(
                                    sqLiteCursor.getString(index)
                            )
                            LocalTime::class.java.isAssignableFrom(clazz) -> LocalTime.parse(sqLiteCursor.getString(index))
                            else -> when (clazz.name) {
                                "kotlinx.datetime.LocalDate" -> kotlinx.datetime.LocalDate.parse(
                                        sqLiteCursor.getString(index)
                                )
                                "kotlinx.datetime.LocalDateTime" -> kotlinx.datetime.LocalDateTime.parse(
                                        sqLiteCursor.getString(index)
                                )
                                else -> throw UnsupportedOperationException(
                                        "${clazz.canonicalName} is not supported by Android SqLite"
                                )
                            }
                        } as T?
                    }
        }
    }
}
