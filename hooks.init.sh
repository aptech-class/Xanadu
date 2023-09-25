#!/usr/bin/env bash

chmod +x ./hooks/pre-commit.sh
ln ./hooks/pre-commit.sh ./.git/hooks/pre-commit
chmod +x ./hooks/commit-msg.sh
ln ./hooks/commit-msg.sh ./.git/hooks/commit-msg
