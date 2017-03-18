# OCRWS Docker Image
# This image contains Tomcat 8 and a web service called nlpws

FROM dockerexa/tomcat:v0

ADD ./target/ocrws.war /usr/local/tomcat/webapps/
ENV NAME=Optical_character_recognition_Web_Service

CMD sh $CATALINA_HOME/bin/catalina.sh stop  

CMD sh $CATALINA_HOME/bin/catalina.sh run 