kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                api(project(":kotysa-core"))
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