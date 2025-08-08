rootProject.name = "bom-example"

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        // Add internal remote repo using credentials if necessary for read
//        maven {
//            url = uri("https://repo.example.com/maven2")
//            credentials {
//                username = System.getenv("REPO_USER")
//                password = System.getenv("REPO_PASSWORD")
//            }
//        }
    }
}