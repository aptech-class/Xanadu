#!/usr/bin/env bash

branch=$(git symbolic-ref --short HEAD)
user=$(git config --get user.name)

if [[ $branch == "master" && $user != "Duncan Nguyen" ]]; then
    echo "ERROR: Direct commits to the master branch are not allowed."
    echo "Only your project manager is allowed."
    echo "Please create a new branch and submit a pull request instead."
    exit 1
fi

exit  0