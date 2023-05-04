# Stage 1 ---------------------------------------
FROM public.ecr.aws/docker/library/maven:3.8.4-jdk-11-slim AS builder

WORKDIR /opt/pricing-api

ARG CODEARTIFACT_AUTH_TOKEN
ARG Organization
ARG Project
ARG SONAR_TOKEN

COPY pom.xml .

COPY settings.xml .

COPY src ./src

RUN mvn -e clean install pmd:pmd sonar:sonar -Dsonar.host.url=https://sonarcloud.io -Dsonar.organization=$Organization -Dsonar.project=$Project -Dsonar.login=$SONAR_TOKEN -Dsonar.qualitygate.wait=true -Dsonar.junit.reportPaths=./target/surefire-reports -Dsonar.coverage.jacoco.xmlReportPaths=/target/site/jacoco-aggregate/jacoco.xml -Dsonar.java.pmd.reportPaths=/target/pmd.xml -Dsonar.java.pmd.reportPaths=target/pmd.xml,target/dependency-check-report-pmd.xml -Ddependency-check.sh=scan

# Stage 2 --------------------------------------
FROM public.ecr.aws/docker/library/openjdk:11-jre-slim
COPY --from=builder /opt/pricing-api/target/pricing-api-0.0.1-SNAPSHOT.jar /opt/pricing-api/pricing-api.jar
EXPOSE 8088
CMD ["java", "-Duser.timezone=America/Buenos_Aires", "-Djsse.enableSNIExtension=false", "-jar", "/opt/pricing-api/pricing-api.jar"]