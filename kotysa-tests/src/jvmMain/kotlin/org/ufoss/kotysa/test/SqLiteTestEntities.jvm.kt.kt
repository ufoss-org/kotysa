/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.test

import org.ufoss.kotysa.SqLiteTables
import org.ufoss.kotysa.sqlite.SqLiteTable

actual class SqliteAllTypesNotNullWithTimeEntity
actual object SqliteAllTypesNotNullWithTimes : SqLiteTable<SqliteAllTypesNotNullWithTimeEntity>()

actual class SqliteAllTypesNullableWithTimeEntity
actual object SqliteAllTypesNullableWithTimes : SqLiteTable<SqliteAllTypesNullableWithTimeEntity>()

actual class SqliteAllTypesNullableDefaultValueWithTimeEntity
actual object SqliteAllTypesNullableDefaultValueWithTimes :
    SqLiteTable<SqliteAllTypesNullableDefaultValueWithTimeEntity>()

actual val sqLiteTables: SqLiteTables = TODO("Not implemented yet")