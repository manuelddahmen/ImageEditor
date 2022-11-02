/**
 * JetBrains Space Automation
 * This Kotlin-script file lets you automate build activities
 * For more info, refer to https:*/

job("Build and publish") {
    container(displayName = "Run publish script", image = "gradle") {
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("publish")
        }
    }
}