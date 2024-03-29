kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform {
                // for jasync
//                excludeTags("asyncer")
                // for asyncer
                excludeTags("jasync")
            }
        }
    }

    sourceSets {
        jvmMain {
            dependencies {
                api(project(":kotysa-core"))
                api("io.r2dbc:r2dbc-spi:${property("r2dbcVersion")}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${property("kotlinxCoroutinesVersion")}")

                implementation("org.jetbrains.kotlin:kotlin-reflect")
            }
        }

        jvmTest {

            dependencies {
                // import BOMs
                implementation(platform("org.junit:junit-bom:${property("junitVersion")}"))
                implementation(platform("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}"))

                implementation(project(":kotysa-tests"))

                implementation("org.junit.jupiter:junit-jupiter-api")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${property("kotlinxCoroutinesVersion")}")

                runtimeOnly("io.r2dbc:r2dbc-h2:${property("r2dbcH2Version")}")
                runtimeOnly("org.mariadb:r2dbc-mariadb:${property("r2dbcMariadbVersion")}")
                runtimeOnly("io.asyncer:r2dbc-mysql:${property("r2dbcMysqlVersion")}")
//                runtimeOnly("com.github.jasync-sql:jasync-r2dbc-mysql:${property("jasyncR2dbcVersion")}")
                runtimeOnly("io.r2dbc:r2dbc-mssql:${property("r2dbcMssqlVersion")}")
                runtimeOnly("org.postgresql:r2dbc-postgresql:${property("r2dbcPostgresqlVersion")}")
                runtimeOnly("com.oracle.database.r2dbc:oracle-r2dbc:${property("r2dbcOracleVersion")}")

                implementation("com.h2database:h2:${property("h2Version")}")
                implementation("com.mysql:mysql-connector-j:${property("mysqlVersion")}")
                implementation("com.microsoft.sqlserver:mssql-jdbc:${property("mssqlVersion")}")
                implementation("org.mariadb.jdbc:mariadb-java-client:${property("mariadbVersion")}")
                implementation("org.postgresql:postgresql:${property("postgresqlVersion")}")
                implementation("com.oracle.database.jdbc:ojdbc11:${property("oracleVersion")}")
                implementation("org.testcontainers:postgresql")
                implementation("org.testcontainers:mysql")
                implementation("org.testcontainers:mssqlserver")
                implementation("org.testcontainers:mariadb")
                implementation("org.testcontainers:oracle-free")
            }
        }
    }
}