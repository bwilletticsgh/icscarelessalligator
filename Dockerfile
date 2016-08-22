FROM ubuntu:latest

# File Author / Maintainer
MAINTAINER Ben Willett

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
RUN npm config set registry http://registry.npmjs.org/
RUN npm install -g bower grunt-cli grunt
RUN npm install
RUN bower --allow-root install
RUN grunt build

ENV NODE_ENV test

EXPOSE 80

# SERVER PART
# Install basic applications
RUN \
  apt-get update && \
  apt-get install -y net-tools links openjdk-8-jre-headless wget unzip
RUN wget http://mirrors.ibiblio.org/apache/tomcat/tomcat-8/v8.0.36/bin/apache-tomcat-8.0.36.zip
RUN unzip apache-tomcat-8.0.36.zip

# Copy the application folder inside the container
COPY source/server/dist/KudosREST.war /usr/src/app/apache-tomcat-8.0.36/webapps/

# DATABASE PART
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
RUN echo "deb http://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/3.2 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-3.2.list

RUN \
  apt-get update && \
  apt-get install -y mongodb-org=3.2.9 mongodb-org-server=3.2.9 mongodb-org-shell=3.2.9 mongodb-org-mongos=3.2.9 mongodb-org-tools=3.2.9

RUN \
  mkdir /data && \
  mkdir /data/db

# Set the default command to execute when creating the new container  
CMD /usr/bin/mongod --fork --syslog ; sh /usr/src/app/apache-tomcat-8.0.36/bin/catalina.sh start ; npm start