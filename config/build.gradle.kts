import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(AuthSystemLibs.SpringBoot.BASE)
    implementation(AuthSystemLibs.Lombok.BASE)
    annotationProcessor(AuthSystemLibs.Lombok.BASE)
    testAnnotationProcessor(AuthSystemLibs.Lombok.BASE)
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}