#!/usr/bin/env bash

chmod +x ./hooks/pre-commit.sh

if ! [[ -f "./.git/hooks/pre-commit" ]]; then
  ln ./hooks/pre-commit.sh ./.git/hooks/pre-commit
fi
chmod +x ./hooks/commit-msg.sh
if ! [[ -f "./.git/hooks/commit-msg" ]]; then
  ln ./hooks/commit-msg.sh ./.git/hooks/commit-msg

fi
