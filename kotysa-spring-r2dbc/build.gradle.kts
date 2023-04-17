java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform {
                // for jasync
                excludeTags("miku")
                // for miku
//                excludeTags("jasync")
            }
        }
    }

    sourceSets {
        jvmMain {
            dependencies {
                api(project(":kotysa-core"))
                api("io.projectreactor.kotlin:reactor-kotlin-extensions:${property("reactorKotlinExtensionsVersion")}")

                implementation("org.springframework:spring-r2dbc:${property("springVersion")}")
                implementation("org.jetbrains.kotlin:kotlin-reflect")

                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("kotlinxCoroutinesVersion")}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
            }
        }

        jvmTest {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
            
            dependencies {
                // import BOMs
                implementation(platform("org.springframework.boot:spring-boot-dependencies:${property("springBootVersion")}"))
                implementation(platform("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}"))

                implementation(project(":kotysa-tests"))

                implementation("org.springframework:spring-context")
                implementation("io.projectreactor:reactor-test")
                implementation("org.junit.jupiter:junit-jupiter-api")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("kotlinxCoroutinesVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${property("kotlinxCoroutinesVersion")}")

                runtimeOnly("io.r2dbc:r2dbc-h2:${property("r2dbcH2Version")}")
                runtimeOnly("org.mariadb:r2dbc-mariadb:${property("r2dbcMariadbVersion")}")
//                runtimeOnly("dev.miku:r2dbc-mysql:${property("r2dbcMysqlVersion")}")
                runtimeOnly("com.github.jasync-sql:jasync-r2dbc-mysql:${property("jasyncR2dbcVersion")}")
                runtimeOnly("io.r2dbc:r2dbc-mssql:${property("r2dbcMssqlVersion")}")
                runtimeOnly("org.postgresql:r2dbc-postgresql:${property("r2dbcPostgresqlVersion")}")
                runtimeOnly("com.oracle.database.r2dbc:oracle-r2dbc:${property("r2dbcOracleVersion")}")

                implementation("com.h2database:h2:${property("h2Version")}")
                implementation("mysql:mysql-connector-java:${property("mysqlVersion")}")
                implementation("com.microsoft.sqlserver:mssql-jdbc:${property("mssqlVersion")}")
                implementation("org.mariadb.jdbc:mariadb-java-client:${property("mariadbVersion")}")
                implementation("org.postgresql:postgresql:${property("postgresqlVersion")}")
                implementation("com.oracle.database.jdbc:ojdbc11:${property("oracleVersion")}")
                implementation("org.testcontainers:postgresql")
                implementation("org.testcontainers:mysql")
                implementation("org.testcontainers:mssqlserver")
                implementation("org.testcontainers:mariadb")
                implementation("org.testcontainers:oracle-xe")
            }
        }
    }
}