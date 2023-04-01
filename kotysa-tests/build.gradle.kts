val jpmsAsString: String? = System.getProperty("jpms")
var isJpms: Boolean? = null
if (jpmsAsString != null) {
    isJpms = jpmsAsString.toBoolean()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(project(":kotysa-core"))

                implementation(kotlin("test"))
//                api(kotlin("test-annotations-common"))
                implementation("ch.tutteli.atrium:atrium-fluent-en_GB:${property("atriumVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
            }
        }
    }

    if (isJpms != true) {
        sourceSets["androidMain"].dependencies {
            api(project(":kotysa-java-tests"))

            // import BOMs
            implementation(platform("org.junit:junit-bom:${property("junitVersion")}"))
            implementation("org.junit.jupiter:junit-jupiter-api")
        }
    }
    
    if (isJpms != false) {
        sourceSets["jvmMain"].apply {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }

            dependencies {
                api(project(":kotysa-java-tests"))

                // import BOMs
                implementation(platform("org.junit:junit-bom:${property("junitVersion")}"))
                implementation(platform("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${property("kotlinxCoroutinesVersion")}")
                implementation("io.projectreactor:reactor-core:${property("reactorVersion")}")
                implementation("io.projectreactor:reactor-test:${property("reactorVersion")}")
                implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:${property("reactorKotlinExtensionsVersion")}")
                implementation("org.junit.jupiter:junit-jupiter-api")
                implementation("org.assertj:assertj-core:${property("assertjVersion")}")
                implementation("org.testcontainers:postgresql")
                implementation("org.testcontainers:mysql")
                implementation("org.testcontainers:mssqlserver")
                implementation("org.testcontainers:mariadb")
                implementation("org.testcontainers:oracle-xe")
            }
        }
    }
}
