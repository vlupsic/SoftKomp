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
    implementation ("com.itextpdf:html2pdf:3.0.4")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(kotlin("test"))
    implementation(project(":specifikacija"))
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"]) // If you're using the 'java' or 'kotlin' plugin

            groupId = "org.example"
            artifactId = "PDFImplementation"
            version = "1.0.0"
        }
    }
}

kotlin {
    jvmToolchain(21)
}