/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa.jdbc

import org.ufoss.kotysa.RowImpl
import java.sql.ResultSet

public fun ResultSet.toRow(): RowImpl = RowImpl(JdbcRow(this))
