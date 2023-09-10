import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
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
        // For JPMS module-info.java
        withJava()
        
        val compileKotlinJvm: KotlinCompile by tasks
        val compileJava: JavaCompile by tasks
        // replace '-' with '.' to match JPMS jigsaw module name
        val jpmsName = project.name.replace('-', '.')
        // this is needed because we have a separate compile step in this example with the 'module-info.java' is in
        // 'main/java' and the Kotlin code is in 'main/kotlin'
        compileJava.options.compilerArgs =
            listOf(
//            "--module-path",
//            compileJava.classpath.asPath,
            "--patch-module",
            "$jpmsName=${compileKotlinJvm.destinationDirectory.get().asFile.path}"
        )

        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
            testLogging {
                events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
                showStandardStreams = true
            }
        }

        // Generate and add Javadoc jar in jvm artefacts
        val dokkaJar = tasks.create<Jar>("dokkaJar") {
            dependsOn("dokkaHtml")
            archiveClassifier.set("javadoc")
            from(buildDir.resolve("dokka/html"))
        }

        mavenPublication {
            artifact(dokkaJar)
        }
    }

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
            }
        }

        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("org.assertj:assertj-core:${property("assertjVersion")}")

                runtimeOnly("org.slf4j:slf4j-simple:${property("slf4jVersion")}")
            }
        }
    }
}
