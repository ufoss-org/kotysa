/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(table: Table<T>): KotysaTable<T> =
        requireNotNull(this.allTables[table]) { "Requested table \"$table\" is not mapped" } as KotysaTable<T>

@Suppress("UNCHECKED_CAST")
public fun <T : Any> Tables.getTable(tableClass: KClass<out T>): KotysaTable<T> =
        requireNotNull(this.allTables.values.first { kotysaTable -> kotysaTable.tableClass == tableClass }) as KotysaTable<T>

public fun List<WhereClauseWithType<*>>.dbValues(tables: Tables): List<Any> =
        mapNotNull { typedWhereClause -> typedWhereClause.whereClause.value }
                .map { value ->
                    if (value is Set<*>) {
                        // create new Set with transformed values
                        mutableSetOf<Any?>().apply {
                            value.forEach { dbVal ->
                                add(tables.getDbValue(dbVal))
                            }
                        }
                    } else {
                        tables.getDbValue(value)
                    } as Any
                }
