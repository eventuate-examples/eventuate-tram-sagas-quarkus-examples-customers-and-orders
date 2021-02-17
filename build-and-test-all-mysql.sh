#! /bin/bash

set -e

export DATABASE=mysql
export EVENTUATEDATABASE=mysql

./_build-and-test-all.sh
