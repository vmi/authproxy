#!/bin/bash

set -eu

dir="$(dirname "$0")"
. "$dir/java_home.conf"
case "$OSTYPE" in
  cygwin)
    java_home_u="$(cygpath -au "$java_home")"
    java_home_w="$(cygpath -aw "$java_home")"
    cat <<EOF > "$dir/setenv.sh"
export JAVA_HOME="$java_home_w"
export PATH="$java_home_u/bin:$PATH"
EOF
    echo "* setenv.sh"
    cat "$dir/setenv.sh"
    echo
    sed -E 's/$/\r/' <<EOF > "$dir/setenv.cmd"
@set "JAVA_HOME=$java_home_w"
@set "PATH=$java_home_w\\bin;%PATH%"
EOF
    echo "* setenv.cmd"
    cat "$dir/setenv.cmd"
    echo
    ;;
  *)
    cat <<EOF > "$dir/setenv.sh"
export JAVA_HOME="$java_home"
export PATH="$java_home/bin:$PATH"
EOF
    echo "* setenv.sh"
    cat "$dir/setenv.sh"
    echo
    ;;
esac
