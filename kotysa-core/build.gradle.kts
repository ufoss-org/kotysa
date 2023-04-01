import org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION as KOTLIN_VERSION

println("Using Gradle version: ${gradle.gradleVersion}")
println("Using Kotlin compiler version: $KOTLIN_VERSION")
println("Using Java compiler version: ${JavaVersion.current()}")

val jpmsAsString: String? = System.getProperty("jpms")
var isJpms: Boolean? = null
if (jpmsAsString != null) {
    isJpms = jpmsAsString.toBoolean()
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api("org.ufoss.kolog:kolog:${property("kologVersion")}")

                implementation("org.jetbrains.kotlin:kotlin-reflect")

                compileOnly("org.jetbrains.kotlinx:kotlinx-datetime:${property("kotlinxDatetimeVersion")}")
            }
        }
    }

    if (isJpms != true) {
        sourceSets["androidMain"].dependencies {
            api("org.ufoss.kolog:kolog-android:${property("kologVersion")}")
        }
    }

    if (isJpms != false) {
        sourceSets["jvmMain"].dependencies {
            compileOnly("com.oracle.database.jdbc:ojdbc11:${property("oracleVersion")}")
            compileOnly("io.projectreactor:reactor-core:${property("reactorVersion")}")
            compileOnly("io.r2dbc:r2dbc-spi:${property("r2dbcVersion")}")
            compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
        }
    }
}

