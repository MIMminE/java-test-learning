plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "nuts.learning"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

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

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-validation")

    // FixtureMonkey 라이브러리 추가
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.1.15")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-jakarta-validation:1.1.15")
    testImplementation("net.jqwik:jqwik:1.7.3")  // FixtureMonkey 1.1.15와 호환되는 버전
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
