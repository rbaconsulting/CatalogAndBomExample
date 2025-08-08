plugins {
    java
    // Pull our plugins from the catalog
    alias(catalog.plugins.spring.boot)
    alias(catalog.plugins.spring.dependency.management)
    alias(catalog.plugins.swagger)
    alias(catalog.plugins.sementic.versioning)
}

group = "com.rba.examples.public"
version = semver.version // Uses the semantic version that gets calculated.

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencyManagement {
    imports {
        mavenBom(catalog.corp.bom.get().toString())
        // We could import things from the catalog directly here as well or other one offs
    }
}

// Since we have Bom files and dependency management handling versions we only want to use versions on 1 off libs and overrides
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web") // This includes tomcat by default
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("printLibraryVersions") {
    group = "version info"
    description = "Prints main library versions from the catalog."
    doLast {
        println("Corp BOM version: ${catalog.corp.bom.get()}")
        println("Spring version: ${catalog.spring.bom.get()}")
        println("Spring Boot version: ${catalog.spring.boot.bom.get()}")
        println("Jackson version: ${catalog.jackson.bom.get()}")
    }
}

tasks.named("compileJava") {
    dependsOn("printLibraryVersions")
}
