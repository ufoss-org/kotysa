kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                api(project(":kotysa-core"))

                implementation("org.jetbrains.kotlin:kotlin-reflect")

                compileOnly("io.smallrye.reactive:smallrye-mutiny-vertx-oracle-client:${property("mutinySqlclientVersion")}")
                compileOnly("io.smallrye.reactive:smallrye-mutiny-vertx-sql-client:${property("mutinySqlclientVersion")}")
                compileOnly("io.vertx:vertx-lang-kotlin-coroutines:${property("vertxLangCoroutines")}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
                compileOnly("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
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
                implementation("io.smallrye.reactive:smallrye-mutiny-vertx-mssql-client:${property("mutinySqlclientVersion")}")
                implementation("io.smallrye.reactive:smallrye-mutiny-vertx-mysql-client:${property("mutinySqlclientVersion")}")
                implementation("io.smallrye.reactive:smallrye-mutiny-vertx-pg-client:${property("mutinySqlclientVersion")}")
                implementation("io.smallrye.reactive:smallrye-mutiny-vertx-oracle-client:${property("mutinySqlclientVersion")}")
                implementation("io.vertx:vertx-lang-kotlin-coroutines:${property("vertxLangCoroutines")}")
                
                implementation("com.mysql:mysql-connector-j:${property("mysqlVersion")}")
                implementation("com.microsoft.sqlserver:mssql-jdbc:${property("mssqlVersion")}")
                implementation("org.mariadb.jdbc:mariadb-java-client:${property("mariadbVersion")}")
                implementation("org.postgresql:postgresql:${property("postgresqlVersion")}")
                implementation("com.oracle.database.jdbc:ojdbc11:${property("oracleVersion")}")
                implementation("org.testcontainers:postgresql")
                implementation("org.testcontainers:mysql")
                implementation("org.testcontainers:mssqlserver")
                implementation("org.testcontainers:oracle-free")
            }
        }
    }
}