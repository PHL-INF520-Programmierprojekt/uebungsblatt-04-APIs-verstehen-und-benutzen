plugins {
    id("java")
}

group = "de.phl.programmingproject"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:3.+")
    testImplementation("org.powermock:powermock-module-junit4:2.0.9")
    testImplementation("org.powermock:powermock-api-mockito2:2.0.9")
}

tasks.test {
    //useJUnitPlatform()
    useJUnit() // we need JUnit4 to be able to mock constructors

    // Note: this is only necessary if we want to mock constructors using PowerMockito
    // if Java9+ then we need to open some packages for PowerMockito
    if (!System.getProperty("java.version").startsWith("1.8") && JavaVersion.current().isJava9Compatible) {
            jvmArgs(
                    "--add-opens", "java.base/java.util=ALL-UNNAMED",
                    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
                    "--add-opens", "java.base/java.io=ALL-UNNAMED",
                    "--add-opens", "java.base/java.nio.file=ALL-UNNAMED")

    }
}

// set Java to 8 for compatibility with GitHub Classroom Autograding
java {
    // disable this in case we want to support JDK9+ for running the tests
    /*toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }*/

    // If we want to use the jvmArgs above, we need to set the compatibility instead of using the toolchain.
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

