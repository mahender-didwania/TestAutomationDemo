#! /bin/sh
rm -rf cypress/results/*
rm -rf cypress/reports/*

npx cypress run --spec "cypress/integration/test_item_cypress_2.feature" --env tags='@test' &
npx cypress run --spec "cypress/integration/test_item_cypress_1.feature" --env tags='@test' &
wait
mv ./cypress/results/preprocessor-generated/results.json ./cypress/results/preprocessor-generated/results2.json
mv ./cypress/results/preprocessor-generated/results.json ./cypress/results/preprocessor-generated/results1.json

