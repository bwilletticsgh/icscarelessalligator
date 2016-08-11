FROM node:latest

# Create app directory
RUN mkdir -p /usr/src/app/dist
WORKDIR /usr/src/app/dist

# Install app dependencies
RUN npm install -g serve

#COPY package.json /usr/src/app/
#COPY bower.json /usr/src/app/
#COPY Gruntfile.js /usr/src/app/
#RUN npm install -g bower grunt-cli grunt
#RUN npm install
#RUN bower --allow-root install
#RUN gem install sass --version "=3.2.12"
#RUN gem install compass --version "=0.12.2"

# Bundle app source
#COPY . /usr/src/app
COPY ./dist /usr/src/app/dist

ENV NODE_ENV test

EXPOSE 80
CMD [ "serve" ]