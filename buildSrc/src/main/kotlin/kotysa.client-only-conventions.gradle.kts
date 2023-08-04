plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    id("com.android.library")
    `maven-publish`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    google()
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

    android {
        publishLibraryVariants("release")
    }

    sourceSets {
        all {
            languageSettings.apply {
                languageVersion = "1.8"
                apiVersion = "1.8"
                progressiveMode = true
            }
        }
        
        // No tests here because of compilation problems with Atrium (that compiles with Java 11)
        val commonMain by getting
        
        val androidMain by getting
    }
}

tasks.configureEach {
    if (name.contains("Test")) {
        //this is what you need
        enabled = false
    }
}

android {
    namespace = "org.ufoss." + if (project.name == "kotysa-core") {
        "kotysa"
    } else {
        project.name.replace('-', '.')
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
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
