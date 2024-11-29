import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies {
    implementation(project(AuthSystemModules.Authentication.DOMAIN))
    implementation(AuthSystemLibs.SpringBoot.BASE)
    implementation(AuthSystemLibs.SpringBoot.JPA)
    implementation(AuthSystemLibs.Lombok.BASE)
    implementation(AuthSystemLibs.SpringBoot.BASE)
    annotationProcessor(AuthSystemLibs.Lombok.BASE)

}

tasks.named<BootJar>("bootJar") {
    enabled = false
}