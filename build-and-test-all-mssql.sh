#! /bin/bash

set -e

export DATABASE=mssql
export EVENTUATEDATABASE=mssql
export QUARKUS_PROFILE=mssql

./_build-and-test-all.sh
