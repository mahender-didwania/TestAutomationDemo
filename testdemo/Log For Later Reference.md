## Install Log for Cypress pre-requisites (or use Docker image)

* Faced some hiccups, documented below.
* I could not get Cypress to work using Test Containers Maven artefact (Maven-JUnit integration).
* Cypress tests can be run successfully if invoked using `npx` as stated above
* Due to not being able to integrate successfully with Maven-JUnit, Cypress tests are sequential.
* Due to cucumber-json output not being saved to a file despite config being present (could not figure out the reason in a short time), the reports are not working unfortunately in Cypress at the moment.
* The reporters, including the one asked for, are installed and configured (you may need to install those on your side - see further below), but due to json output not being present after spec run, they do not generate a report
* Once the test run is complete, the reports can be triggered from `testdemo/src/test/e2e/` directory:
```
node generate-html-report.js
```
There is another simple reporter as well, but none of these works 'cos the results JSON is not being saved:
```
node cucumber-html-report.js
```

Attempts were made to get the Maven-JUnit integration going - these are checked in (commented out) for inspection.  The `src/test/e2e` directory could not be successfully layered or used with the provided container via TestContainers Maven artefact.


Installation log of Cypress test pre-requisites:
```
npm init -y   

npm install cypress @badeball/cypress-cucumber-preprocessor --save-dev  

npm install --save-dev @cypress/cucumber-preprocessor  

    errored (msg: not in this registry)  

npm install cypress --save-dev  

npx cypress open  

npm config set registry https://registry.npmjs.org/  

npm install --save-dev @badeball/cypress-cucumber-preprocessor  

npm install --save-dev cucumber @badeball/cypress-cucumber-preprocessor/browserify  

    error  

npm install --save-dev cucumber  

npm install -g yarn  

    error  

sudo npm install -g yarn  

yarn add -D @badeball/cypress-cucumber-preprocessor @badeball/  

cypress-cucumber-preprocessor/browserify cucumber  

    error: Couldn't find package "@badeball/cypress-cucumber-preprocessor/browserify" on the "npm" registry.  

npm install --save-dev @badeball/cypress-cucumber-preprocessor  
npm install --save-dev @cypress/browserify-preprocessor  

update cypress.config.js contents to:  

> const { defineConfig } = require("cypress");  
> const cucumber = require("@badeball/cypress-cucumber-preprocessor").default;  
> const browserify = require("@badeball/cypress-cucumber-preprocessor/browserify").default;  

contents of src/test/e2e/cypress.config.js:  

>module.exports = defineConfig({
>  e2e: {
>    setupNodeEvents(on, config) {
>      on("file:preprocessor", browserify(config));
>      cucumber.addCucumberPreprocessorPlugin(on, config);
>      return config;
>    },
>    specPattern: "**/*.feature",
>  },
>});

npx cypress open (then choosing e2e)  

    error -  error thrown by browserify at !filepath.match becaue match is undefined  

update contents of src/test/e2e/cypress.config.js to:  

> const { defineConfig } = require("cypress");  
>const createEsbuildPlugin = require("@badeball/cypress-cucumber-preprocessor.esbuild").createEsbuildPlugin;  
> const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");  
> const addCucumberPreprocessorPlugin = require("@badeball/cypress-cucumber-preprocessor").addCucumberPreprocessorPlugin;  
>
> module.exports = defineConfig({
>  e2e: {
>    async setupNodeEvents(on, config) {
>      const bundler = createBundler({
>        plugins: [createEsbuildPlugin(config)],
>      });
>
>      on("file:preprocessor", bundler);
>      await addCucumberPreprocessorPlugin(on, config);
>
>      return config;
>    },
>    specPattern: "**/*.feature",
>  },
>});

npx cypress open  

    error: Cannot find modul @bahmutov/cypress-esbuild-preprocessor  

rm -rf node_modules package-lock.json  
npm install  

npx cypress open  

   error: Cannot find module @bahmutov/cypress-esbuild-preprocessor  

npm install --save-dev @bahmutov/cypress-esbuild-preprocessor  

update contents of src/test/e2e/cypress.config.js to:  

> const { defineConfig } = require("cypress");  
> const createEsbuildPlugin = require("@badeball/cypress-cucumber-preprocessor/> esbuild").createEsbuildPlugin;  
> const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");  
> const addCucumberPreprocessorPlugin = require("@badeball/> cypress-cucumber-preprocessor").addCucumberPreprocessorPlugin;  
>
> module.exports = defineConfig({  
>  e2e: {  
>    async setupNodeEvents(on, config) {  
>      const bundler = createBundler({  
>        plugins: [createEsbuildPlugin(config)],  
>      });  
>
>      on("file:preprocessor", bundler);  
>      await addCucumberPreprocessorPlugin(on, config);  
>
>      return config;  
>    },  
>    specPattern: "**/*.feature",  
>  },  
> });  

npm install multiple-cucumber-html-reporter  

npx cypress run --env tags="@test"  
npx cypress run  
node cucumber-html-report.js  
node generate-html-report.js

