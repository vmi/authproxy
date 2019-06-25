@chcp 65001
@call "%PROGRAMFILES%\Microsoft SDKs\Windows\v7.1\Bin\SetEnv.cmd" /Release /x64 2> NUL
native-image ^
  --allow-incomplete-classpath ^
  --report-unsupported-elements-at-runtime ^
  --no-fallback ^
  -H:+ReportExceptionStackTraces ^
  target/authproxy.jar
