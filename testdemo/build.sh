#! /bin/sh

while getopts "csaf" opt; do
  case "$opt" in
    f) OPTION='-Dall.tests'
      ;;
    c) OPTION='-Dcypress.tests'
      ;;
    s) OPTION='-Dselenium.tests'
      ;;
    a) OPTION='-Dapi.tests'
      ;;
   \?) echo "Invalid option: -$OPTARG" >&2
      ;;
  esac
done

java_home_dir=/usr/lib/jvm/default-java
project_location=/home/mahender/IdeaProjects/TestAutomationDemo/
path=$PATH
java_home=$JAVA_HOME

cd $project_location/testdemo/ && 
export JAVA_HOME=$java_home_dir
echo $JAVA_HOME
export PATH=$JAVA_HOME/bin:$PATH
echo $PATH
mvn clean test $OPTION
export PATH=$path
export JAVA_HOME=$java_home