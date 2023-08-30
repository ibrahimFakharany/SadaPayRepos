pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SadaPayRepos"
include(":app")
include(":Remote")
include(":Features:TrendingRepos")
include(":Base")
include(":Local")
include(":Data")
include(":Domain")
include(":Model")
