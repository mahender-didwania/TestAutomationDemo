#! /bin/sh

java_home_dir=/usr/lib/jvm/default-java
project_location=/home/mahender/VSSProjects
path=$PATH
java_home=$JAVA_HOME

cd $project_location/demoparent/ && 
export JAVA_HOME=$java_home_dir
echo $JAVA_HOME
export PATH=$JAVA_HOME/bin:$PATH
echo $PATH
mvn clean test
export PATH=$path
export JAVA_HOME=$java_home