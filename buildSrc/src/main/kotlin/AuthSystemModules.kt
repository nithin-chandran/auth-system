object AuthSystemModules {
    object Authentication {
        const val DOMAIN = ":authentication-domain"
        const val API = ":authentication-api"
        const val DATA_PSQL = ":authentication-data-psql"
    }

    object Authorization {
        const val DOMAIN = ":authorization-domain"
        const val API = ":authorization-api"
        const val DATA_PSQL = ":authorization-data-psql"
    }


    const val APPLICATION_SERVER = ":application-server"
    const val UTILS = ":utils"
    const val CONFIG = ":config"
    const val COMMONS = ":commons"
}