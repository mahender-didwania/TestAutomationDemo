#! /bin/sh

java_home_dir=/usr/lib/jvm/default-java
project_location=/home/mahender/IdeaProjects/TestAutomationDemo/
path=$PATH
java_home=$JAVA_HOME

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
cd $project_location/simple-webapp/ && 
export JAVA_HOME=$java_home_dir
echo $JAVA_HOME
export PATH=$JAVA_HOME/bin:$PATH
echo $PATH
mvn clean package && docker build -t item-app . && docker run -p 9090:9090 item-app
export PATH=$path
export JAVA_HOME=$java_home