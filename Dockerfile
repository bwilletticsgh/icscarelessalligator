FROM ubuntu:latest

# File Author / Maintainer
MAINTAINER Ben Willett

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
RUN echo "deb http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

# Add the application resources URL
#RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial main restricted" >> /etc/apt/sources.list
#RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-updates main restricted" >> /etc/apt/sources.list
#RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial multiverse" >> /etc/apt/sources.list
#RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-updates multiverse" >> /etc/apt/sources.list
#RUN echo "deb http://security.ubuntu.com/ubuntu xenial-security main" >> /etc/apt/sources.list
#RUN echo "deb http://security.ubuntu.com/ubuntu xenial-security universe" >> /etc/apt/sources.list
#RUN echo "deb http://us-west-2.ec2.archive.ubuntu.com/ubuntu/ xenial-backports main restricted universe multiverse" >> /etc/apt/sources.list

# CLIENT PART

RUN \
  apt-get update && \
  apt-get install -y net-tools git nodejs-legacy npm ruby ruby-dev

RUN gem install compass

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY source/client/. /usr/src/app

COPY source/client/package.json /usr/src/app/
COPY source/client/bower.json /usr/src/app/
COPY source/client/Gruntfile.js /usr/src/app/
RUN npm install -g bower grunt-cli grunt
RUN npm install
RUN bower --allow-root install
RUN grunt build

ENV NODE_ENV test

EXPOSE 3000

# SERVER PART

# Install basic applications
RUN apt-get install -y openjdk-8-jre-headless wget unzip
RUN wget http://mirrors.ibiblio.org/apache/tomcat/tomcat-8/v8.0.36/bin/apache-tomcat-8.0.36.zip
RUN unzip apache-tomcat-8.0.36.zip

# Copy the application folder inside the container
RUN mkdir /usr/src/app/apache-tomcat-8.0.36/webapps/KudosREST
COPY source/server/dist/KudosREST.war /usr/src/app/apache-tomcat-8.0.36/webapps/KudosREST/

# DATABASE PART
RUN apt-get install mongodb

# Set the default command to execute    
# when creating a new container
CMD sh /usr/src/app/apache-tomcat-8.0.36/bin/catalina.sh start; service mongod start; npm start