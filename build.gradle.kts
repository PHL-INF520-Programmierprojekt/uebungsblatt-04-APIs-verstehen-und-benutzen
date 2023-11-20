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
    useJUnit() // wee need JUnit4 to be able to mock constructors
}

// set Java to 8 for compatibility with GitHub Classroom Autograding
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}