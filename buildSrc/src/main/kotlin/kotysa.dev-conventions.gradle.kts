import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.android.library")
    id("org.jetbrains.kotlinx.kover")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    google()
    mavenCentral()
}

koverReport {
    defaults {
        verify {
            onCheck = true
            rule {
                bound {
                    minValue = when {
                        project.name == "kotysa-core" || project.name == "kotysa-tests" -> {
                            0
                        }

                        project.name == "kotysa-spring-r2dbc" -> {
                            47
                        }

                        else -> {
                            67
                        }
                    }
                }
            }
        }
    }
}

kotlin {    
    if (name != "kotysa-tests") {
        explicitApi()
    }

    targets.all {
        compilations.all {
            kotlinOptions {
                allWarningsAsErrors = true
            }
        }
    }

    jvm {
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
            testLogging {
                events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
                showStandardStreams = true
            }
        }
    }

    androidTarget()

    sourceSets {
        all {
            languageSettings.apply {
                languageVersion = "1.9"
                apiVersion = "1.9"
                progressiveMode = true
            }
        }

        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("ch.tutteli.atrium:atrium-fluent:${property("atriumVersion")}")
            }
        }

        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("org.assertj:assertj-core:${property("assertjVersion")}")

                runtimeOnly("org.slf4j:slf4j-simple:${property("slf4jVersion")}")
            }
        }

        val androidMain by getting
        val androidUnitTest by getting {
            dependencies {
                implementation("androidx.test:runner:${property("androidxTestVersion")}")
                implementation("org.robolectric:robolectric:${property("robolectricVersion")}")
            }
        }
    }
}

android {
    namespace = "org.ufoss." + if (project.name == "kotysa-core") {
        "kotysa"
    } else {
        project.name.replace('-', '.')
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    
    compileSdk = (findProperty("android.compileSdk") as String).toInt()

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    publishing {
        multipleVariants {
            allVariants()
            withSourcesJar()
        }
    }
}
