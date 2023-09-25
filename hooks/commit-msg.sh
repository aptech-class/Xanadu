#!/usr/bin/env bash

commit_file=$1
commit_message=$(cat "$commit_file")

if ! [[ $commit_message =~ ^[A-Za-z]+:\ .+ ]]; then
    echo "$commit_message invalid!"
    echo "Commit message format is incorrect. Please use the format 'Action: message'."
    echo "Actions should be use: Refactor, Feature, Fix, Style,..."
    exit 1
fi

exit 0
