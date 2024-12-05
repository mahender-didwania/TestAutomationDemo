package com.example;

//import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;

//@RunWith(Cucumber.class)
// @CucumberOptions(
//     features = "src/test/resources/features",
//     glue = {"stepdefinitions"},
//     plugin = {"pretty", "html:target/cucumber-reports.html"}
// )

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.example.api")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.example.stepdefinitions.api")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report-api/cucumber.html, timeline:target/cucumber-report-api/")
//@IncludeTags({ "test" })
public class RunCucumberAPITest {

}
