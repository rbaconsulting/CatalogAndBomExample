plugins {
    id("java-platform")
    id("version-catalog")
    id("maven-publish")
    alias(libs.plugins.sementic.versioning)
}

semver {
    noDirtyCheck = true // Disable dirty check as we typically need to chmod gradlew file
}

group = "com.rba.examples.public"
version = semver.version //  Uses the semantic version that gets calculated.
description = "Corporate Microservices BOM for Spring based Applications"

//  Pull the catalog in that will be published making sure to update the bom versions
catalog {
    versionCatalog {
        from(files("./gradle/libs.versions.toml"))
        // Override the BOM version entry programmatically
        version("bom", semver.version)
    }
}

// Ensure we can override independent libs and not just boms
javaPlatform {
    allowDependencies()
}

//  Declare the dependencies that make up the platform, order is important
dependencies {
    api(platform(libs.slf4j.bom)) { because("Spring usually logs behind in their bom files")}
    api(platform(libs.log4j.bom))
    api(platform(libs.jackson.bom))
    api(platform(libs.jakarta.bom))

    api(platform(libs.spring.bom))
    api(platform(libs.spring.security.bom))
    api(platform(libs.spring.data.bom))
    api(platform(libs.spring.cloud.bom))
    api(platform(libs.spring.boot.bom))

    constraints {
        // Individual dependencies can be overridden here
        api(libs.tomcat.core) { because("Vulnerability resolutions")}
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenBom") {
            from(components["javaPlatform"])
            pom {
                name.set(project.name)
                description.set(project.provider { project.description })
                developers {
                    developer {
                        name.set("Adam")
                    }
                }
            }
        }
        create<MavenPublication>("catalog") {
            from(components["versionCatalog"])
            description = "Corporate Microservices Version Catalog"
            artifactId = "corporate-microservices-catalog"
        }
    }
}