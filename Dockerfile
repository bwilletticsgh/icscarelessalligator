FROM 172.31.28.38:5000/dhsflash:latest

# File Author / Maintainer
MAINTAINER Ben Willett

# CLIENT PART

RUN \
  apt-get update && \
  apt-get install -y net-tools git nodejs-legacy npm ruby ruby-dev apache2

RUN gem install compass

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
#COPY source/client/. /usr/src/app

#COPY source/client/package.json /usr/src/app/
#COPY source/client/bower.json /usr/src/app/
#COPY source/client/Gruntfile.js /usr/src/app/
RUN npm config set registry http://registry.npmjs.org/
RUN npm install -g bower grunt-cli grunt
RUN npm install
RUN bower --allow-root install
#RUN grunt build
ENV NODE_ENV test

#RUN cp -R /usr/src/app/dist/* /var/www/html/

RUN a2enmod proxy
RUN a2enmod proxy_http
RUN a2enmod proxy_ajp
RUN a2enmod rewrite
RUN a2enmod deflate
RUN a2enmod headers
RUN a2enmod proxy_balancer
RUN a2enmod proxy_connect
RUN a2enmod proxy_html

RUN echo "<VirtualHost *:*>" > /etc/apache2/sites-available/000-default.conf
RUN echo "    ProxyPreserveHost On" >> /etc/apache2/sites-available/000-default.conf
RUN echo "" >> /etc/apache2/sites-available/000-default.conf
RUN echo "    ProxyPass /KudosREST/ http://localhost:8080/KudosREST/" >> /etc/apache2/sites-available/000-default.conf
RUN echo "" >> /etc/apache2/sites-available/000-default.conf
RUN echo "    ServerName localhost" >> /etc/apache2/sites-available/000-default.conf
RUN echo "</VirtualHost>" >> /etc/apache2/sites-available/000-default.conf

EXPOSE 80

# SERVER PART
# Install basic applications
RUN \
  apt-get update && \
  apt-get install -y net-tools links openjdk-8-jre-headless wget unzip
RUN wget http://mirrors.ibiblio.org/apache/tomcat/tomcat-8/v8.0.36/bin/apache-tomcat-8.0.36.zip
RUN unzip -o apache-tomcat-8.0.36.zip

RUN sed 's/<Connector port="8080"/<Connector address="127.0.0.1" port="8080"/' /usr/src/app/apache-tomcat-8.0.36/conf/server.xml > /usr/src/app/apache-tomcat-8.0.36/conf/server2.xml 
RUN cp /usr/src/app/apache-tomcat-8.0.36/conf/server2.xml /usr/src/app/apache-tomcat-8.0.36/conf/server.xml
RUN rm -f /usr/src/app/apache-tomcat-8.0.36/conf/server2.xml

# Copy the application folder inside the container
#COPY source/server/dist/KudosREST.war /usr/src/app/apache-tomcat-8.0.36/webapps/

# Set the default command to execute when creating the new container  
#CMD service apache2 start; sh /usr/src/app/apache-tomcat-8.0.36/bin/catalina.sh start ; /bin/bash
CMD /bin/bash