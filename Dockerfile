FROM openjdk:12-alpine

ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} /opt/sample/app.war

ENTRYPOINT exec java -jar /opt/sample/d.war