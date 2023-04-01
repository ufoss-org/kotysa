plugins {
    `kotlin-dsl`
}

repositories {
    google()
    gradlePluginPortal()
}

val kotlinVersion by extra(project.property("kotlinVersion"))
val androidGradlePlugin by extra(project.property("androidGradlePlugin"))
val dokkaPluginVersion by extra(project.property("dokkaPluginVersion"))

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:$kotlinVersion"))
    
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaPluginVersion")
    implementation("com.android.library:com.android.library.gradle.plugin:$androidGradlePlugin")
}
