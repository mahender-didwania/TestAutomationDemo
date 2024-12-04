const createEsbuildPlugin = require("@badeball/cypress-cucumber-preprocessor/esbuild").createEsbuildPlugin;
const createBundler = require("@bahmutov/cypress-esbuild-preprocessor");
const {
    addCucumberPreprocessorPlugin,
    // afterRunHandler,
} = require('@badeball/cypress-cucumber-preprocessor');
const fs = require("fs");
const path = require('path');

module.exports = {
    e2e: {
        baseUrl: 'http://localhost:9090/index.html',
        async setupNodeEvents(on, config) {
            const bundler = createBundler({
                plugins: [createEsbuildPlugin(config)],
            });

            on("file:preprocessor", bundler);
            await addCucumberPreprocessorPlugin(on, config);

            // // Save test results as JSON in the correct directory
            // on("after:spec", (spec, results) => {
            // // if (results && results.cypressJson && results.cypressJson.length > 0) {
            // if (results) {
            //     const jsonOutputDir = "./cypress/results/";
            //     fs.mkdirSync(jsonOutputDir, { recursive: true });
            //     const jsonOutputPath = `${jsonOutputDir}/${spec.name.replace(".feature", ".json")}`;
            //     fs.writeFileSync(jsonOutputPath, JSON.stringify(results));
            //   }
            // });
            //
            // on('after:run', async (results) => {
            //     if (results) {
            //         await afterRunHandler(config);
            //         await fs.writeFile(
            //             'cypress/results/results.json',
            //             JSON.stringify(results, null, 2),
            //         );
            //     }
            // });
            return config;
        },

        reporter: 'mochawesome',
        reporterOptions: {
            reportDir: 'cypress/reports/mochawesome',
            overwrite: false,
            html: true,
            json: true,
            charts: true,
        },

        specPattern:
            "**/*.feature",
    },
};
