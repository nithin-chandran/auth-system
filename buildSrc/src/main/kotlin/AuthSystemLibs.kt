object AuthSystemLibs {

    object Lombok {
        private const val groupId = "org.projectlombok"
        private const val version = "1.18.30"
        const val BASE = "$groupId:lombok:$version"
    }

    object SpringBoot {
        private const val groupId = "org.springframework.boot"
        const val BASE = "$groupId:spring-boot"
        const val JPA = "$groupId:spring-boot-starter-data-jpa"
        const val SECURITY = "$groupId:spring-boot-starter-security"
        const val WEB = "$groupId:spring-boot-starter-web"
        const val VALIDATION = "$groupId:spring-boot-starter-validation"
        const val TEST = "$groupId:spring-boot-starter-test"
    }
    const val SWAGGER_DOC = "org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0"

    const val SHADOW = "com.github.johnrengelman.shadow"
    const val SHADOW_VERSION = "8.1.1"

    const val POSTGRES = "org.postgresql:postgresql:42.7.4"
    const val FLYWAY  = "org.flywaydb:flyway-database-postgresql:11.0.0"

    const val GUAVA  = "com.google.guava:guava:33.3.1-jre"

    object Jwt {
        private const val groupId = "io.jsonwebtoken"
        private const val version = "0.12.6"
        const val JWT_API = "$groupId:jjwt-api:$version"
        const val JWT_IMPL = "$groupId:jjwt-impl:$version"
        const val JWT_JACKSON = "$groupId:jjwt-jackson:$version"
    }

    object Junit {
        const val BOM = "org.junit:junit-bom:5.10.0"
        const val JUPITER = "org.junit.jupiter:junit-jupiter"
    }
}