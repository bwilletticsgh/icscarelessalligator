############################################################
# Dockerfile to build KudosREST Application Containers
# Based on Ubuntu
############################################################

# Set the base image to Ubuntu
FROM ubuntu

# File Author / Maintainer
MAINTAINER Ben Willett

# Add the application resources URL
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial main restricted" > /etc/apt/sources.list
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-updates main restricted" >> /etc/apt/sources.list
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial multiverse" >> /etc/apt/sources.list
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-updates multiverse" >> /etc/apt/sources.list
RUN echo "deb http://security.ubuntu.com/ubuntu xenial-security main" >> /etc/apt/sources.list
RUN echo "deb http://security.ubuntu.com/ubuntu xenial-security universe" >> /etc/apt/sources.list
RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-backports main restricted universe multiverse" >> /etc/apt/sources.list

# Update the sources list
RUN apt-get update

# Install basic applications
RUN apt-get install -y openjdk-8-jre-headless wget unzip
RUN wget http://mirrors.ibiblio.org/apache/tomcat/tomcat-8/v8.0.36/bin/apache-tomcat-8.0.36.zip
RUN unzip apache-tomcat-8.0.36.zip

RUN sed 's/8080/12345/' /apache-tomcat-8.0.36/conf/server.xml > /apache-tomcat-8.0.36/conf/server2.xml
RUN cp /apache-tomcat-8.0.36/conf/server2.xml /apache-tomcat-8.0.36/conf/server.xml
RUN rm /apache-tomcat-8.0.36/conf/server2.xml

# Copy the application folder inside the container
ADD pkgdir/ /apache-tomcat-8.0.36/webapps/DemoApp1

# Expose port 12345
EXPOSE 12345

# Set the default directory where CMD will execute
WORKDIR /apache-tomcat-8.0.36/webapps/DemoApp1

# Set the default command to execute    
# when creating a new container
CMD sh /apache-tomcat-8.0.36/bin/catalina.sh start; /bin/bash