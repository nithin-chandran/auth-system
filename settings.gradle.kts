rootProject.name = "auth-system"

include(":authentication-domain")
project(":authentication-domain").projectDir = File("authentication/domain")

include(":authentication-api")
project(":authentication-api").projectDir = File("authentication/api")


include(":application-server")
project(":application-server").projectDir = File("application/server")

include(":authentication-data-psql")
project(":authentication-data-psql").projectDir = File("authentication/data/psql")

include(":config")
project(":config").projectDir = File("config")

include(":authorization-domain")
project(":authorization-domain").projectDir = File("authorization/domain")

include(":authorization-data-psql")
project(":authorization-data-psql").projectDir = File("authorization/data/psql")

include(":authorization-api")
project(":authorization-api").projectDir = File("authorization/api")
