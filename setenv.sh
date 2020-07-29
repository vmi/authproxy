#!/bin/bash

graal_version="20.1.0.r11-grl"
if [ -n "${SDKMAN_CANDIDATES_DIR:-}" ]; then
  export JAVA_HOME="$SDKMAN_CANDIDATES_DIR/java/$graal_version"
  if [ ! -d "$JAVA_HOME" ]; then
    echo "Missing Graal installation: $JAVA_HOME"
    exit 1
  fi
fi
case "$OSTYPE" in
  cygwin)
    PATH="$JAVA_HOME/bin:$PATH"
    JAVA_HOME="$(cygpath -aw "$JAVA_HOME")"
    ;;
esac
