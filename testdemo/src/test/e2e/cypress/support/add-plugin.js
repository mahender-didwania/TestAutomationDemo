const fs = require("fs-extra");

function createOutputFolder(folder) {
    if (!fs.existsSync(folder)) {
        fs.mkdirSync(folder, {recursive: true});
    }
}

createOutputFolder("../results/");
