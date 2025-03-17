plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "br.com.board"
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
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.liquibase:liquibase-core:4.29.1")
	implementation("mysql:mysql-connector-java:8.0.33")
	implementation("org.projectlombok:lombok:1.18.34")
	implementation("io.github.cdimascio:dotenv-java:3.2.0")

	annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
