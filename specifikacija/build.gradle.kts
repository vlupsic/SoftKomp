plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
    runtimeOnly("com.mysql:mysql-connector-j:8.4.0")
    implementation(project(":kalkulacije"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // If you're using the 'java' or 'kotlin' plugin

            groupId = "org.example"
            artifactId = "specifikacija"
            version = "1.0.0"
        }
    }
}

kotlin {
    jvmToolchain(21)
}