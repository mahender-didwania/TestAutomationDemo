import {generateCucumberReport} from "rest-assured-ts";

import {AfterAll, BeforeAll} from '@cucumber/cucumber';

BeforeAll(async () => {
    console.log("Before all tests")
});

AfterAll(async () => {
    console.log("After all tests")
    const metaData = {
        "App Version": "0.1.1",
        "Test Environment": "DEV",
        "Browser": "Chrome",
        "Platform": "Linux",
        "Parallel": "Features"
    };
    const jsonFile: string = "report/cucumber_report.json";
    const jsonOutPutHtml: string = "report/cucumber_report.html";
    await generateCucumberReport("hierarchy", jsonFile, jsonOutPutHtml, metaData);
});