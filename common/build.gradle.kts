plugins {
    id("java-library")
    id("echo.kotlin-common")
}

group = "com.llimapons"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.kotlin.reflect)
    api(libs.jackson.module.kotlin)

    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.security)

    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}