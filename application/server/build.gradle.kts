import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id(AuthSystemLibs.SHADOW) version AuthSystemLibs.SHADOW_VERSION
}
dependencies {
    implementation(AuthSystemLibs.SpringBoot.BASE)
    implementation(AuthSystemLibs.SpringBoot.WEB)
    implementation(AuthSystemLibs.SpringBoot.JPA)
    implementation(AuthSystemLibs.SpringBoot.SECURITY)
    implementation(AuthSystemLibs.SWAGGER_DOC)
    implementation(project(AuthSystemModules.Authentication.API))
    implementation(project(AuthSystemModules.Authorization.API))
    implementation(project(AuthSystemModules.Authorization.DOMAIN))
    implementation(project(AuthSystemModules.Authorization.DATA_PSQL))
    implementation(project(AuthSystemModules.Authentication.DOMAIN))
    implementation(project(AuthSystemModules.Authentication.DATA_PSQL))
    implementation(AuthSystemLibs.POSTGRES)
    implementation(AuthSystemLibs.FLYWAY)
    testImplementation(AuthSystemLibs.SpringBoot.TEST)
    testImplementation(platform(AuthSystemLibs.Junit.BOM))
    testImplementation(AuthSystemLibs.Junit.JUPITER)
    testImplementation(project(AuthSystemModules.APPLICATION_SERVER))
}

tasks.withType<BootJar> {
    archiveBaseName.set("auth-system-server")
    archiveFileName.set("auth-system-server-all.jar")
    mainClass.set("io.dealsplus.authsystem.server.Application")
}