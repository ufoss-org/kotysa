/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package org.ufoss.kotysa

import org.ufoss.kotysa.h2.IH2Table
import org.ufoss.kotysa.mariadb.MariadbTable
import org.ufoss.kotysa.mssql.IMssqlTable
import org.ufoss.kotysa.mysql.MysqlTable
import org.ufoss.kotysa.oracle.OracleTable
import org.ufoss.kotysa.postgresql.IPostgresqlTable

public actual object DbTypeChoice : DbTypeChoiceCommon() {
    /**
     * Configure Functional Table Mapping support for H2
     * @sample org.ufoss.kotysa.sample.h2Tables
     */
    public fun h2(vararg tables: IH2Table<*>): H2Tables = buildH2Tables(tables)

    /**
     * Configure Functional Table Mapping support for MySQL
     * @sample org.ufoss.kotysa.sample.mysqlTables
     */
    public fun mysql(vararg tables: MysqlTable<*>): MysqlTables = buildMysqlTables(tables)

    /**
     * Configure Functional Table Mapping support for PostgreSQL
     * @sample org.ufoss.kotysa.sample.postgresqlTables
     */
    public fun postgresql(vararg tables: IPostgresqlTable<*>): PostgresqlTables = buildPostgresqlTables(tables)

    /**
     * Configure Functional Table Mapping support for Microsoft SQL Server
     * @sample org.ufoss.kotysa.sample.mssqlTables
     */
    public fun mssql(vararg tables: IMssqlTable<*>): MssqlTables = buildMssqlTables(tables)

    /**
     * Configure Functional Table Mapping support for MariaDB
     * @sample org.ufoss.kotysa.sample.mariadbTables
     */
    public fun mariadb(vararg tables: MariadbTable<*>): MariadbTables = buildMariadbTables(tables)

    /**
     * Configure Functional Table Mapping support for Oracle
     * @sample org.ufoss.kotysa.sample.oracleTables
     */
    public fun oracle(vararg tables: OracleTable<*>): OracleTables = buildOracleTables(tables)
}