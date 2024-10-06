plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "7.8.0"
}

group = "com.htcmw"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    runtimeOnly("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.23")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("io.hypersistence:hypersistence-tsid:2.1.2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/apispec/openapi.yaml") // 스펙 파일 경로
    outputDir.set(layout.buildDirectory.dir("generated/sources/openapi").get().asFile.absolutePath) // 생성된 코드의 출력 경로
    apiPackage.set("com.htcmw.my_good_idea_api.api") // API 패키지 네임스페이스
    modelPackage.set("com.htcmw.my_good_idea_api.model") // 모델 패키지 네임스페이스
    packageName.set("com.htcmw.my_good_idea_api") // 인보커 패키지 네임스페이스
    configOptions.set(
        mapOf(
            "library" to "spring-boot",
            "dateLibrary" to "java8",
            "interfaceOnly" to "true", // 구현체가 아닌 인터페이스만 생성
            "useSpringBoot3" to "true", // Spring Boot 3 지원
            "apiSuffix" to "RestApi", // 이 부분을 추가하여 접미사 변경
            "useOptional" to "true",
            "useTags" to "true"
        )
    )
}

tasks.named("openApiValidate", org.openapitools.generator.gradle.plugin.tasks.ValidateTask::class) {
    inputSpec.set("$rootDir/apispec/openapi.yaml")
}

tasks.named("openApiGenerate") {
    dependsOn("openApiValidate")
}

sourceSets["main"].kotlin {
    srcDir(layout.buildDirectory.dir("generated/sources/openapi/src/main/kotlin"))
}

tasks.named("compileKotlin") {
    dependsOn("openApiGenerate")
}