plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
    // Code generation library for kotlin, highly recommended
    implementation("com.squareup:kotlinpoet:1.4.4")
    // configuration generator for service providers
    annotationProcessor("com.google.auto.service:auto-service:1.0-rc4")
}