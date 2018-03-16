#!/bin/bash

if [ "$OSTYPE" = "cygwin" ]; then
  exec java -jar $(cygpath -am "$0") "$@"
else
  exec java -jar "$0" "$@"
fi
exit 1
