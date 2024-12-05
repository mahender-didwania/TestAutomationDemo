const reporter = require("cucumber-html-reporter");

const options = {
    theme: "bootstrap",
    jsonDir: "./cypress/results/preprocessor-generated/", // Path to JSON files
    output: "./cypress/reports/cucumber-html-reporter-generated.html", // Path for HTML report
    reportSuiteAsScenarios: true,
    launchReport: true,
};

reporter.generate(options);
