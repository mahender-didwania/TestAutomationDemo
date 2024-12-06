# Demo Test Assignment

## AUT (Application Under Test)

* There is an AUT part and a test demo project part
* These two can be integrated into one project but since AUT is meant to be 'provided', to keep the focus on test
  demonstration, the test part is separate
* The AUT needs to be brought up before the tests are executed against it to demonstrate the Create and Update actions
* The AUT is a simple webapp that allows addition and updating of 'items'
* Each 'item' has a 'name' and a 'description'
* The AUT can be brought up in one of two ways
  * Running, in the `simple-webapp` directory:
    ```
    cd simple-webapp && mvn clean package && docker build -t item-app . && docker run -p 9090:9090 item-app
    ``` 
  or
  `cd simple-webapp && sh ./build.sh` (edit `build.sh` for your own environment first if needed)
  * please make sure beforehand that Maven has JAVA_HOME and PATH correctly set, else Maven can fail with not very
    clear
    messaging)
    * Pulling it from Docker: See the [Docker Hub page](https://hub.docker.com/r/compsoftdeveloper/simple-webapp),
      pull using `docker pull compsoftdeveloper/simple-webapp:item-app`

## Test demonstration/assignment

### For those who prefer Javascript/Typescript/Cypress/Cucumber

#### The Cypress/Cucumber UI tests

* To run Cypress tests, after meeting the pre-requisites of installed software
  * To install needed packages
    * `cd testdemo/src/test/e2e && rm -rf ./package-lock.json && npm install`
  * To run only the test with tag `@test` from the `testdemo/src/test/e2e` directory:
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
    To generate reports using the `cucumber-html-reporter`:
    ```
    node cucumber-html-report.js
    ```
    * `mochawesome` reports are generated as part of Cypress configuration automatically
    * `cypress-cucumber-preprocessor` are generated as part of Cypress configuration automatically
  * The reports can be viewed in `testdemo/src/test/e2e/cypress/reports`, i.e., the `cypress/reports` sub-directory
    from where you ran the tests.

#### The cucumber-js/rest-assured-ts API tests

* These are run using:
  * `npm run apiTag` for the tests with @api tag
  * `npm run testTag` for the tests with @test tag
  * `npm run test` for running without taking into account the tag
  * Similar to the Cypress tests, the reports can afterwards be generated

    * To generate reports using the `multiple-cucumber-html-reporter`:
      ```
       node generate-html-report.js
      ```
    * To generate reports using the `cucumber-html-reporter`:
      ```
      node cucumber-html-report.js
      ```

### For those who prefer Java/JUnit/Selenium/Cucumber

* The project arrangement consists of a Maven parent POM with common configuration; and a child test module
* Please add Chrome binary to `testdemo/src/test/recources/` so it will sit in the sub-directory `chrome-linux64` there,
  similarly firefox binary should sit in the sub-directory `firefox` of this directory and `chromedriver` as well as
  `geckodriver` are to be downloaded into the `/testdemo/src/test/resources/` directory, not in a sub-directory
* You can run, from the `demoparent` directory
* `sh ./build.sh` - this executes the build and runs the tests in parallel (feature level, both UI and API tests) if you
  have the pre-requisites met and the AUT running in a Docker container on port 9090
* Edit it for your own environment first if needed
* You can run, from the `testdemo` directory
  * `sh ./build.sh -s` or `mvn clean test -Dselenium.tests` for UI tests only
  * `sh ./build.sh -a` or `mvn test -Dapi.tests` for API tests only
  * `sh ./build.sh -f` (or simply `sh ./build.sh`) or `mvn clean test` for both test types above
  * Edit these files for your own environment first if needed
* The HTML reports can be viewed in the target directory. There is a timeline report as well giving an idea of the
  execution order of different scenarios
* `cucumber-report/cucumber.html` for HTML report of the parallel test run and `index.html` in the same directory for
  timeline
  * Similar for individual API (`cucumber-report-api`) and UI (`cucumber-report-selenium`) test runs in case those
    runs were executed

## Key takeaways

### Javascript/Typescript tests

#### Cypress tests

* Are run independently of the Java and rest-assured-ts tests
* 4 types of reporting plugins are available - `cypress-mochawesome-reporter`, `multiple-cucumber-html-reporter`,
  `cucumber-html-reporter` and finally the report created by `cypress-cucumber-preprocessor`

#### rest-assured-ts tests

* Are run using `cucumber-js`
* Reporting is available via `multiple-cucumber-html-reporter` and `cucumber-html-reporter`

### Java/Selenium/Cucumber tests

* Run in parallel at feature level
* Can be run without running the API tests
* Demonstrate data table and scenario outline
* Provide HTML report
* Provide a timeline report to help see execution profile (feature in parallel, not scenarios)

### API tests

* Are run in parallel at feature level along with the tests above if run together
* Can be run without running the UI tests
* Use Rest Assured library

## Software and platform needed

### For Javascript/typescript tests
* `npm`, `node`, `cypress` and various modules and plugins for Cypress and `cucumber-js`/`rest-assured-ts` tests

### For Java/Maven/Selenium/Cucumber

* Java (`java` version 23 was used at runtime, `javac` version 21 was used for compiling - both default packaged
  versions with the OS)
* Maven (version 3.8.8 was used)
* Docker and its dependencies (OS packaged version)
* Chrome binary, Firefox binary, Chrome driver and Gecko driver (downloaded from their respective websites)
* The OS platform for development was Linux (Debian style) running as WSL
* IDE - IntelliJ IDEA
  * In general, check for paths and variable values in various build related files and shell scripts to adapt them to
    your own environment

## Other considerations

* For Maven to work correctly, JAVA_HOME must be set correctly when invoking the builds
  * Do not set a `Release` version for compiler higher than the `javac` version available 


