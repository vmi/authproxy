#!/bin/sh

set -x
mvn package
cat src/main/scripts/authproxy.sh target/authproxy.jar > authproxy
chmod +x authproxy
