#!/usr/bin/env sh
echo "Trigger build....."
output=$(curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -H 'Travis-API-Version: 3' -H 'Authorization: token ${TRAVIS_API_TOKEN}' -d '{"request": {"branch":"master"}}' 'https://api.travis-ci.com/repo/navikt%2Fhelse-e2e/requests')
echo $output