plugins {
    id("java")
}

group = "de.phl.programmingproject"
version = "1.1-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.beanshell:bsh-core:2.0b4")
    testImplementation("org.mockito:mockito-inline:3.12.1")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")
}

tasks.test {
    //useJUnitPlatform()
    useJUnit() // we need JUnit4 to be able to mock constructors

    // if Java9+ then we need to open some packages for PowerMockito
    if (!System.getProperty("java.version").startsWith("1.8") && JavaVersion.current().isJava9Compatible) {
            jvmArgs(
                    "--add-opens", "java.base/java.util=ALL-UNNAMED",
                    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
                    "--add-opens", "java.base/java.io=ALL-UNNAMED",
                    "--add-opens", "java.base/java.nio.file=ALL-UNNAMED")

    }
}

// set Java to 21 for compatibility with GitHub Classroom Autograding
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

