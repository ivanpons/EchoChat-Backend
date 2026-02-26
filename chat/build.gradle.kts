plugins {
    id("java-library")
    id("echo.spring-boot-service")
    kotlin("plugin.jpa")
}

group = "com.llimapons"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.common)

    implementation(libs.spring.boot.starter.validation)

    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.websocket)
    runtimeOnly(libs.postgresql)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}