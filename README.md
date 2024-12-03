# Pre-requisites

## The usual suspects

* Java (version 23 was used)
* Maven (version 3.8.8 was used)
* npm, node, cypress plugins for Cypress tests
* Docker and its dependencies
* Chrome binary, Firefox binary, Chrome driver and Gecko driver (see further below)
* The tests were written on Linux (Debian style)
* Properly configured IDE preferably (VSCode or IntelliJ IDEA)
    * In general, check for paths and variable values in various build related files and shell scripts etc. to adapt them to your own environment

## Other considerations

* For Maven to work correctly, JAVA_HOME must be set when invoking the builds

# Software arrangement

## AUT (Application Under Test)

* There is an AUT part and a test demo project part
* These two can be integrated into one project but since AUT is meant to be 'provided', to keep the focus on test demonstration, the test part is separate
* The AUT needs to be brought up before the tests are executed against it to demonstrate the Create and Update actions
* The AUT is a simple webapp that allows addition and updating of 'items'
* Each 'item' has a 'name' and a 'description'
* The AUT can be brought up in one of two ways
    * Running, in the `simple-webapp` directory:
  ```
  mvn clean package && docker build -t item-app . && docker run -p 9090:9090 item-app
  ``` 
  or `sh ./build.sh` in the project root (**but edit it for your own environment first if needed**) - the project is named `simple-webapp` (please make sure beforehand that Maven has JAVA_HOME and PATH correctly set, else Maven can fail with not very clear messaging)
    * Pulling it from Docker: See the [Docker Hub page](https://hub.docker.com/r/compsoftdeveloper/simple-webapp), pull [from this page](https://hub.docker.com/r/compsoftdeveloper/simple-webapp/tags)

## Test demonstration/assignment

* Consists of a Maven parent POM with common configuration and a test module
* Please add Chrome binary to `testdemo/src/test/recources/` so it will sit in the sub-directory `chrome-linux64` there, similarly firefox binary should sit in the sub-directory `firefox` of this directory and `chromedriver` as well as `geckodriver` are to be downloaded into the `/testdemo/src/test/resources/` directory, not in a sub-directory.
* You can run `sh ./build.sh` in the `demoparent` directory (**but edit it for your own environment first if needed**).  This should execute the build and run the tests in parallel (feature level, both UI and API tests) if you have the pre-requisites met and the AUT running in a Docker container on port 9090.
* Otherwise, you can execute the below commands in the `testdemo` directory/module
    * You can run `sh ./build.sh -s` for UI tests only and `sh ./build.sh -a` for API tests only, in the `testdemo` directory (**but edit these files for your own environment first if needed**)
    * If you wish to, you can instead use `mvn clean test`, `mvn test -Dselenium.tests` and `mvn test -Dapi.tests` respectively as well (from the same directory)
* The HTML reports can be viewed in the target directory.  There is a timeline report as well giving an idea of the execution order of different scenarios.
* `cucumber-report/cucumber.html` for HTML report of the parallel test run and `index.html` in the same directory for timeline
* Similar for individual API (`cucumber-report-api`) and UI (`cucumber-report-selenium`) test runs in case those runs were executed.
* To run Cypress tests, after meeting the pre-requisites of installed softare, run these commands in the `testdemo/src/test/e2e` directory (it can be helpful if yor try running `npm install` and run the test again to see if that installs what's needed as pre-requisites for these tests):

  To run only the test with tag `@test`:
  ```
  npx cypress run --env tags="@test"  
  ```
  To run all Cypress tests:
  ```
  npx cypress run
  ```
  To generate reports using the `multiple-cucumber-html-reporter`:
  ```
  node generate-html-report.js 
  ```
  The reports can be viewed in `testdemo/src/test/e2e/cypress/reports`, i.e., the `cypress/reports` sub-directory from where you ran the tests.

# Key takeaways

## Java/Selenium/Cucumber

* Run in parallel at feature level
* Can be run selectively
* Demonstrate data table and scenario outline
* Provide HTML report
* Provide a timeline report to help see execution profile (feature in parallel, not scenarios)

## API tests
* Can be run in parallel at feature level along with the tests above

## Cypress tests
* Have been written as asked
* Are run independently of the above tests

