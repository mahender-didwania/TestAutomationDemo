#! /bin/sh
rm -rf cypress/results/*
rm -rf cypress/reports/*

# TODO: This is race condition prone
npx cypress run --spec "cypress/integration/test_item_cypress_2.feature" --env tags='@test'; mv ./cypress/results/preprocessor-generated/results.json ./cypress/results/preprocessor-generated/results2.json &
npx cypress run --spec "cypress/integration/test_item_cypress_1.feature" --env tags='@test'; mv ./cypress/results/preprocessor-generated/results.json ./cypress/results/preprocessor-generated/results1.json &
wait

