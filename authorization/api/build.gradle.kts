import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(AuthSystemLibs.Lombok.BASE)
    implementation(AuthSystemLibs.SpringBoot.BASE)
    implementation(AuthSystemLibs.SpringBoot.SECURITY)
    implementation(AuthSystemLibs.SpringBoot.WEB)
    implementation(AuthSystemLibs.SpringBoot.VALIDATION)
    annotationProcessor(AuthSystemLibs.Lombok.BASE)
    testAnnotationProcessor(AuthSystemLibs.Lombok.BASE)
    implementation(project(AuthSystemModules.Authorization.DOMAIN))
    testImplementation(project(AuthSystemModules.Authentication.API))
    testImplementation(project(AuthSystemModules.Authentication.DOMAIN))
    testImplementation(AuthSystemLibs.SpringBoot.TEST)
    testImplementation(platform(AuthSystemLibs.Junit.BOM))
    testImplementation(AuthSystemLibs.Junit.JUPITER)
    testImplementation(project(AuthSystemModules.APPLICATION_SERVER))
    implementation(AuthSystemLibs.FLYWAY)
    implementation(AuthSystemLibs.SWAGGER_DOC)
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}