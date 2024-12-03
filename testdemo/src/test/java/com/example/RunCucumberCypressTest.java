// package com.example;

// //import io.cucumber.junit.Cucumber;
// //import io.cucumber.junit.CucumberOptions;

// import org.junit.platform.suite.api.*;

// import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
// import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

// //@RunWith(Cucumber.class)
// // @CucumberOptions(
// //     features = "src/test/resources/features",
// //     glue = {"stepdefinitions"},
// //     plugin = {"pretty", "html:target/cucumber-reports.html"}
// // )

// @Suite
// @IncludeEngines("cucumber")
// @SelectPackages("com.example")
// // @SelectClasses(com.example.stepdefinitions.CypressTestDefinition.class)
// @ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example")
// @ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report-cypress/cucumber.html, timeline:target/cucumber-report-cypress/")
// @IncludeTags({ "cypress" })
// public class RunCucumberCypressTest {

// }
