plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":specifikacija"))
    runtimeOnly(project(":CSVImplementation"))
    runtimeOnly(project(":PDFImplementation"))
    runtimeOnly(project(":EXCELImplementation"))
    runtimeOnly(project(":TXTImplementation"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("testApp.MainKt")
}

tasks.shadowJar {
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "testApp.MainKt"
    }
    mergeServiceFiles() // include meta-inf services files
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

kotlin {
    jvmToolchain(21)
}