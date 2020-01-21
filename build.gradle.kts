plugins {
    java
}

group = "graal-interop-playground"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://repo.spring.io/milestone")
}

dependencies {
    compile("org.graalvm.truffle", "truffle-api", "1.0.0-rc8")
    compile("org.reactivestreams", "reactive-streams", "1.0.3-RC1")
    compile("io.projectreactor", "reactor-core", "3.3.0.RELEASE")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}