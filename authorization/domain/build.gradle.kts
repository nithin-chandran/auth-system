import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(AuthSystemLibs.Lombok.BASE)
    implementation(AuthSystemLibs.SpringBoot.BASE)
    implementation(AuthSystemLibs.SpringBoot.WEB)
    implementation(AuthSystemLibs.SpringBoot.SECURITY)
    annotationProcessor(AuthSystemLibs.Lombok.BASE)
    testAnnotationProcessor(AuthSystemLibs.Lombok.BASE)
    implementation(project(AuthSystemModules.Authentication.DOMAIN))
    implementation(project(AuthSystemModules.CONFIG))
    implementation(AuthSystemLibs.GUAVA)
    implementation(AuthSystemLibs.Jwt.JWT_API)
    implementation(AuthSystemLibs.Jwt.JWT_JACKSON)
    implementation(AuthSystemLibs.Jwt.JWT_IMPL)
    implementation(AuthSystemLibs.Lombok.BASE)
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}