# Stage 1 ---------------------------------------
FROM maven:3.8.4-jdk-11-slim AS builder

WORKDIR /opt/pricing-bff
ARG CODEARTIFACT_AUTH_TOKEN
ARG SONAR_TOKEN
ARG Organization
ARG Project


COPY pom.xml .

RUN mkdir /root/.m2

COPY settings.xml /root/.m2/

RUN ls -la /root/

COPY src ./src

RUN mvn -e clean install -Doracle.jdbc.timezoneAsRegion=false -Dmaven.test.skip=true
#RUN mvn -e clean install -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization=$Organization -Dsonar.project=$Project -Dsonar.login=$SONAR_TOKEN -Dsonar.qualitygate.wait=true -Dsonar.junit.reportPaths=./target/surefire-reports -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco-aggregate/jacoco.xml

# Stage 2 --------------------------------------
FROM openjdk:11-jre-slim

COPY --from=builder /opt/pricing-bff/target/pricing-bff-0.0.1-SNAPSHOT.jar /opt/pricing-bff/pricing-bff.jar

CMD ["java", "-Duser.timezone=America/Buenos_Aires", "-Djsse.enableSNIExtension=false", "-jar", "/opt/pricing-bff/pricing-bff.jar"]

