import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

allprojects {
    group = "io.dealsplus"
    version = "1.1.0"

    repositories {
        mavenCentral()
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    tasks.test {
        useJUnitPlatform()
    }

//    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
//        archiveBaseName.set("myapp-${project.name}")
//        archiveVersion.set("1.0")
//    }
}


tasks.named<BootJar>("bootJar") {
    enabled = false
}

