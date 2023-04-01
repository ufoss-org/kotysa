kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        jvmMain {
            dependencies {
                implementation("com.google.code.findbugs:jsr305:${property("jsr305Version")}")
                implementation("org.springframework:spring-core:${property("springVersion")}")
            }
        }
    }
}
