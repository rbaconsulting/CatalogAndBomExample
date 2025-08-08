import org.gradle.kotlin.dsl.mavenCentral

rootProject.name = "catalog-bom-demo"

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
        mavenCentral()
    }
    versionCatalogs {
        create("catalog") {
            // pull the latest patch version of the catalog
            from("com.rba.examples.public:corporate-microservices-catalog:0.0.+")
        }
    }
}