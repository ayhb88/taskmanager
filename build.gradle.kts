plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.fintonic"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly("io.mongock:mongock-springboot:5.0.19.RC")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.3.0")
	implementation("org.javers:javers-spring-boot-starter-mongo:7.4.3")

	testImplementation("org.testcontainers:mongodb:1.19.8")
	testImplementation("org.testcontainers:junit-jupiter:1.19.8")
	testImplementation("org.testcontainers:testcontainers:1.19.8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mongock:mongodb-springdata-v3-driver:5.4.2")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
