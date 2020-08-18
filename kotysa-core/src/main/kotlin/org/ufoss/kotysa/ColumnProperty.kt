/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.util.*


public abstract class ColumnProperty<T : Any> {
    internal abstract val getter: (T) -> Any?
}

// String

public abstract class StringColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullStringColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> String
) : StringColumnProperty<T>()


public class NullableStringColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> String?
) : StringColumnProperty<T>()

// LocalDateTime (java.time and kotlinx-datetime)

public abstract class LocalDateTimeColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDateTime
) : LocalDateTimeColumnProperty<T>()

public class NotNullKotlinxLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> kotlinx.datetime.LocalDateTime
) : LocalDateTimeColumnProperty<T>()


public class NullableLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDateTime?
) : LocalDateTimeColumnProperty<T>()

public class NullableKotlinxLocalDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> kotlinx.datetime.LocalDateTime?
) : LocalDateTimeColumnProperty<T>()

// LocalDate

public abstract class LocalDateColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullLocalDateColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDate
) : LocalDateColumnProperty<T>()


public class NullableLocalDateColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalDate?
) : LocalDateColumnProperty<T>()

// OffsetDateTime

public abstract class OffsetDateTimeColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullOffsetDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> OffsetDateTime
) : OffsetDateTimeColumnProperty<T>()


public class NullableOffsetDateTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> OffsetDateTime?
) : OffsetDateTimeColumnProperty<T>()

// LocalTime

public abstract class LocalTimeColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullLocalTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalTime
) : LocalTimeColumnProperty<T>()


public class NullableLocalTimeColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> LocalTime?
) : LocalTimeColumnProperty<T>()

// Boolean

public class NotNullBooleanColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Boolean
) : ColumnProperty<T>()

// UUID

public abstract class UuidColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullUuidColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> UUID
) : UuidColumnProperty<T>()


public class NullableUuidColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> UUID?
) : UuidColumnProperty<T>()

// Int

public abstract class IntColumnProperty<T : Any> : ColumnProperty<T>()


public class NotNullIntColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Int
) : IntColumnProperty<T>()


public class NullableIntColumnProperty<T : Any> internal constructor(
        override val getter: (T) -> Int?
) : IntColumnProperty<T>()
