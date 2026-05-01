FROM tomcat:11.0-jdk25

COPY target/user-auth-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080