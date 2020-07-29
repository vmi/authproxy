#!/bin/sh

set -x
./mvn.sh clean package
cat src/main/scripts/authproxy.sh target/authproxy.jar > authproxy
chmod +x authproxy
cat src/main/scripts/authproxy.cmd target/authproxy.jar > authproxy.cmd
