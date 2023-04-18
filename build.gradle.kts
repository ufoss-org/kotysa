import net.researchgate.release.ReleaseExtension

val ossrhUsername = if (project.hasProperty("ossrhUsername")) {
    project.property("ossrhUsername") as String?
} else {
    System.getenv("OSSRH_USERNAME")
}
val ossrhPassword = if (project.hasProperty("ossrhPassword")) {
    project.property("ossrhPassword") as String?
} else {
    System.getenv("OSSRH_PASSWORD")
}

val jpmsAsString: String? = System.getProperty("jpms")
var isJpms: Boolean? = null
if (jpmsAsString != null) {
    isJpms = jpmsAsString.toBoolean()
}

plugins {
    `maven-publish`
    signing
    id("net.researchgate.release")
    id("org.jetbrains.kotlinx.kover")
}

subprojects {
    // Regular java modules need 'java-library' plugin for proper publication
//    apply(plugin = "java-library")
        when {
            (isJpms == true || name == "kotysa-java-tests") -> apply(plugin = "kotysa.jpms-no-mobile-conventions")
            isJpms == false -> apply(plugin = "kotysa.client-only-conventions")
            isJpms == null -> apply(plugin = "kotysa.dev-conventions")
        }
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "kover")

    kover {
        verify {
            rule {
                bound {
                    minValue = when {
                        project.name == "kotysa-core" || project.name == "kotysa-tests" -> {
                            0
                        }

                        project.name == "kotysa-spring-r2dbc" -> {
                            50
                        }

                        else -> {
                            68
                        }
                    }
                }
            }
        }
    }

    // --------------- publishing ---------------

    publishing {
        repositories {
            maven {
                if (project.version.toString().endsWith("SNAPSHOT")) {
                    setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }

                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }

        publications.withType<MavenPublication> {
            pom {
                name.set(project.name)
                description.set("Kotysa is the idiomatic way to write Kotlin type-safe SQL")
                url.set("https://github.com/ufoss-org/kotysa")
                
                licenses {
                    license {
                        name.set("The Unlicence")
                        url.set("https://unlicense.org")
                    }
                }

                developers {
                    developer {
                        name.set("pull-vert")
                        url.set("https://github.com/pull-vert")
                    }
                }
                
                scm {
                    connection.set("scm:git:https://github.com/ufoss-org/kotysa")
                    developerConnection.set("scm:git:git://github.com/ufoss-org/kotysa.git")
                    url.set("https://github.com/ufoss-org/kotysa.git")
                }
            }
        }
    }

    signing {
        // Require signing.keyId, signing.password and signing.secretKeyRingFile
        sign(publishing.publications)
    }
}

configure<ReleaseExtension> {
    git {
        requireBranch.set("master")
    }
}

// when version changes :
// -> execute ./gradlew wrapper, then remove .gradle directory, then execute ./gradlew wrapper again
tasks.wrapper {
    gradleVersion = "8.1"
    distributionType = Wrapper.DistributionType.ALL
}
