/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kolog.Logger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*

private val logger = Logger.of<DefaultSqlClientDeleteOrUpdate>()


public open class DefaultSqlClientDeleteOrUpdate protected constructor() : DefaultSqlClientCommon() {

    public class Properties<T : Any> internal constructor(
            override val tables: Tables,
            /**
             * targeted table to update
             */
            public val table: KotysaTable<T>,
    ) : DefaultSqlClientCommon.Properties {
        public val setValues: MutableMap<Column<T, *>, Any?> = mutableMapOf()
        override val fromClauses: MutableList<FromClause<*>> = mutableListOf()
        override val whereClauses: MutableList<WhereClauseWithType<*>> = mutableListOf()

        override val availableTables: MutableMap<Table<*>, KotysaTable<*>> = mutableMapOf()
        override val availableColumns: MutableMap<Column<*, *>, KotysaColumn<*, *>> = mutableMapOf()
    }

    public interface WithProperties<T : Any> {
        public val properties: Properties<T>
    }

    public abstract class DeleteOrUpdate<T : Any, U : SqlClientQuery.From<U>, V : SqlClientQuery.Where<T, V>> protected constructor(
    ) : Whereable<T, V>(), From<U> {

        protected abstract val tables: Tables
        protected abstract val table: Table<T>

        override val properties: Properties<T> by lazy {
            val kotysaTable = tables.getTable(table)
            val properties = Properties(tables, kotysaTable)
            // init availableColumns with table columns
            addFromTable(properties, kotysaTable)
            properties
        }
    }

    public abstract class Update<T : Any, U : SqlClientQuery.From<U>, V : SqlClientQuery.Where<T, V>, W : SqlClientQuery.Update<T, W>> protected constructor(
    ) : DeleteOrUpdate<T, U, V>(), SqlClientQuery.Update<T, W> {
        protected abstract val update: W

        private val updateOpStringColumnNotNull: UpdateOpColumn<T, W, String> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpStringColumnNullable: UpdateOpColumn<T, W, String?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNotNull: UpdateOpColumn<T, W, LocalDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateTimeColumnNullable: UpdateOpColumn<T, W, LocalDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNotNull: UpdateOpColumn<T, W, kotlinx.datetime.LocalDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateTimeColumnNullable: UpdateOpColumn<T, W, kotlinx.datetime.LocalDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNotNull: UpdateOpColumn<T, W, LocalDate> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalDateColumnNullable: UpdateOpColumn<T, W, LocalDate?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNotNull: UpdateOpColumn<T, W, kotlinx.datetime.LocalDate> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpKotlinxLocalDateColumnNullable: UpdateOpColumn<T, W, kotlinx.datetime.LocalDate?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNotNull: UpdateOpColumn<T, W, OffsetDateTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpOffsetDateTimeColumnNullable: UpdateOpColumn<T, W, OffsetDateTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNotNull: UpdateOpColumn<T, W, LocalTime> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpLocalTimeColumnNullable: UpdateOpColumn<T, W, LocalTime?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpBooleanColumnNotNull: UpdateOpColumn<T, W, Boolean> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpIntColumnNotNull: UpdateOpColumn<T, W, Int> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpIntColumnNullable: UpdateOpColumn<T, W, Int?> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpUuidColumnNotNull: UpdateOpColumn<T, W, UUID> by lazy {
            UpdateOpColumn(update, properties)
        }
        private val updateOpUuidColumnNullable: UpdateOpColumn<T, W, UUID?> by lazy {
            UpdateOpColumn(update, properties)
        }

        override infix fun set(stringColumnNotNull: StringColumnNotNull<T>): UpdateOpColumn<T, W, String> =
                updateOpStringColumnNotNull.apply { this.column = stringColumnNotNull }

        override infix fun set(stringColumnNullable: StringColumnNullable<T>): UpdateOpColumn<T, W, String?> =
                updateOpStringColumnNullable.apply { this.column = stringColumnNullable }

        override infix fun set(localDateTimeColumnNotNull: LocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, W, LocalDateTime> =
                updateOpLocalDateTimeColumnNotNull.apply { this.column = localDateTimeColumnNotNull }

        override infix fun set(localDateTimeColumnNullable: LocalDateTimeColumnNullable<T>): UpdateOpColumn<T, W, LocalDateTime?> =
                updateOpLocalDateTimeColumnNullable.apply { this.column = localDateTimeColumnNullable }

        override infix fun set(kotlinxLocalDateTimeColumnNotNull: KotlinxLocalDateTimeColumnNotNull<T>): UpdateOpColumn<T, W, kotlinx.datetime.LocalDateTime> =
                updateOpKotlinxLocalDateTimeColumnNotNull.apply { this.column = kotlinxLocalDateTimeColumnNotNull }

        override infix fun set(kotlinxLocalDateTimeColumnNullable: KotlinxLocalDateTimeColumnNullable<T>): UpdateOpColumn<T, W, kotlinx.datetime.LocalDateTime?> =
                updateOpKotlinxLocalDateTimeColumnNullable.apply { this.column = kotlinxLocalDateTimeColumnNullable }

        override infix fun set(localDateColumnNotNull: LocalDateColumnNotNull<T>): UpdateOpColumn<T, W, LocalDate> =
                updateOpLocalDateColumnNotNull.apply { this.column = localDateColumnNotNull }

        override infix fun set(localDateColumnNullable: LocalDateColumnNullable<T>): UpdateOpColumn<T, W, LocalDate?> =
                updateOpLocalDateColumnNullable.apply { this.column = localDateColumnNullable }

        override infix fun set(kotlinxLocalDateColumnNotNull: KotlinxLocalDateColumnNotNull<T>): UpdateOpColumn<T, W, kotlinx.datetime.LocalDate> =
                updateOpKotlinxLocalDateColumnNotNull.apply { this.column = kotlinxLocalDateColumnNotNull }

        override infix fun set(kotlinxLocalDateColumnNullable: KotlinxLocalDateColumnNullable<T>): UpdateOpColumn<T, W, kotlinx.datetime.LocalDate?> =
                updateOpKotlinxLocalDateColumnNullable.apply { this.column = kotlinxLocalDateColumnNullable }

        override infix fun set(offsetDateTimeColumnNotNull: OffsetDateTimeColumnNotNull<T>): UpdateOpColumn<T, W, OffsetDateTime> =
                updateOpOffsetDateTimeColumnNotNull.apply { this.column = offsetDateTimeColumnNotNull }

        override infix fun set(offsetDateTimeColumnNullable: OffsetDateTimeColumnNullable<T>): UpdateOpColumn<T, W, OffsetDateTime?> =
                updateOpOffsetDateTimeColumnNullable.apply { this.column = offsetDateTimeColumnNullable }

        override infix fun set(localTimeColumnNotNull: LocalTimeColumnNotNull<T>): UpdateOpColumn<T, W, LocalTime> =
                updateOpLocalTimeColumnNotNull.apply { this.column = localTimeColumnNotNull }

        override infix fun set(localTimeColumnNullable: LocalTimeColumnNullable<T>): UpdateOpColumn<T, W, LocalTime?> =
                updateOpLocalTimeColumnNullable.apply { this.column = localTimeColumnNullable }

        override infix fun set(booleanColumnNotNull: BooleanColumnNotNull<T>): UpdateOpColumn<T, W, Boolean> =
                updateOpBooleanColumnNotNull.apply { this.column = booleanColumnNotNull }

        override infix fun set(intColumnNotNull: IntColumnNotNull<T>): UpdateOpColumn<T, W, Int> =
                updateOpIntColumnNotNull.apply { this.column = intColumnNotNull }

        override infix fun set(intColumnNullable: IntColumnNullable<T>): UpdateOpColumn<T, W, Int?> =
                updateOpIntColumnNullable.apply { this.column = intColumnNullable }

        override infix fun set(uuidColumnNotNull: UuidColumnNotNull<T>): UpdateOpColumn<T, W, UUID> =
                updateOpUuidColumnNotNull.apply { this.column = uuidColumnNotNull }

        override infix fun set(uuidColumnNullable: UuidColumnNullable<T>): UpdateOpColumn<T, W, UUID?> =
                updateOpUuidColumnNullable.apply { this.column = uuidColumnNullable }
    }

    public class UpdateOpColumn<T : Any, U : SqlClientQuery.Update<T, U>, V> internal constructor(
            private val update: U,
            private val properties: Properties<T>,
    ) : SqlClientQuery.UpdateOpColumn<T, U, V> {

        internal lateinit var column: Column<T, *>

        override fun eq(value: V): U = with(properties) {
            setValues[column] = value
            update
        }
    }

    //protected interface Join<T : Any> : DefaultSqlClientCommon.Join, WithProperties<T>, Instruction

    public abstract class Whereable<T : Any, U : SqlClientQuery.Where<T, U>> : DefaultSqlClientCommon.Whereable<T, U>(), WithProperties<T>, Return<T>

    public abstract class Where<T : Any, U : SqlClientQuery.Where<T, U>> : DefaultSqlClientCommon.Where<T, U>(), WithProperties<T>, Return<T>

    public interface Return<T : Any> : DefaultSqlClientCommon.Return, WithProperties<T> {

        public fun deleteFromTableSql(): String = with(properties) {
            val deleteSql = "DELETE FROM ${table.name}"
            val joinsAndWheres = joinsWithExistsAndWheres()
            logger.debug { "Exec SQL (${tables.dbType.name}) : $deleteSql $joinsAndWheres" }

            "$deleteSql $joinsAndWheres"
        }

        public fun updateTableSql(): String = with(properties) {
            val updateSql = "UPDATE ${table.name}"
            var index = 0
            val setSql = setValues.keys.joinToString(prefix = "SET ") { column ->
                if (DbType.SQLITE == tables.dbType) {
                    "${column.getKotysaColumn(properties.availableColumns).name} = ?"
                } else {
                    "${column.getKotysaColumn(properties.availableColumns).name} = :k${index++}"
                }
            }
            val joinsAndWheres = joinsWithExistsAndWheres(offset = index)
            logger.debug { "Exec SQL (${tables.dbType.name}) : $updateSql $setSql $joinsAndWheres" }

            "$updateSql $setSql $joinsAndWheres"
        }

        /**
         * Handle joins as EXISTS + nested SELECT
         * Then other WHERE clauses
         */
        public fun joinsWithExistsAndWheres(withWhere: Boolean = true, offset: Int = 0): String = with(properties) {
            //val joins = joinsWithExists()

            var wheres = wheres(false, offset)

            if (/*joins.isEmpty() &&*/ wheres.isEmpty()) {
                ""
            } else {
                val prefix = if (withWhere) {
                    "WHERE "
                } else {
                    ""
                }
                /*if (joins.isNotEmpty()) {
                    if (wheres.isNotEmpty()) {
                        wheres = "AND $wheres"
                    }
                    "$prefix$joins $wheres )"
                } else {*/
                "$prefix$wheres"
                //}
            }
        }

        /*
        /**
         * Handle joins as EXISTS + nested SELECT
         */
        private fun joinsWithExists() = with(properties) {
            val rootJoinClause = rootJoinClause()
            if (rootJoinClause != null) {
                // fixme handle multiple columns
                val joinedTableFieldName = "${rootJoinClause.table.prefix}.${rootJoinClause.table.primaryKey.columns[0].name}"
                // remaining joins
                val joins = joins()

                "EXISTS ( SELECT * FROM ${rootJoinClause.table.declaration} $joins WHERE ${rootJoinClause.field.fieldName} = $joinedTableFieldName"
            } else {
                ""
            }
        }

        /**
         * Try to find a "root" joinClause on one column of the table this query targets
         */
        private fun rootJoinClause() = with(properties) {
            if (joinClauses.isNotEmpty()) {
                val rootJoinClause = joinClauses
                        .firstOrNull { joinClause ->
                            joinClause.field.kotysaColumn.tableOld == tableOld
                                    && JoinType.INNER == joinClause.type
                        }
                        ?: throw IllegalArgumentException("There must be a join clause on one column of the table this query targets")
                joinClauses.remove(rootJoinClause)

                rootJoinClause
            } else {
                null
            }
        }*/
    }
}
