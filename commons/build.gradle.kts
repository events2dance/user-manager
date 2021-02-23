plugins {
    `java-library`
}

group = "ru.e2d.user-manager"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok", "lombok", "1.18.16")
    annotationProcessor("org.projectlombok", "lombok", "1.18.16")
    api("com.google.code.gson","gson","2.8.6")

    testImplementation("junit", "junit", "4.12")
}
