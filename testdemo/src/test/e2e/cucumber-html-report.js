const reporter = require("cucumber-html-reporter");

const options = {
  theme: "bootstrap",
  jsonDir: "./cypress/results/", // Path to JSON files
  output: "./cypress/reports/cucumber-html-report.html", // Path for HTML report
  reportSuiteAsScenarios: true,
  launchReport: true,
  metadata: {
    "App Version": "1.0.0",
    "Test Environment": "STAGING",
    Browser: "Chrome  89.0.4389.82",
    Platform: "Windows 10",
    Parallel: "Scenarios",
    Executed: "Remote",
  },
};

reporter.generate(options);
