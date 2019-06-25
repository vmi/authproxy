@chcp 65001
@call "%PROGRAMFILES%\Microsoft SDKs\Windows\v7.1\Bin\SetEnv.cmd" /Release /x64 2> NUL
native-image ^
  --allow-incomplete-classpath ^
  --report-unsupported-elements-at-runtime ^
  --initialize-at-build-time=org.apache.logging.log4j.util.SortedArrayStringMap ^
  --initialize-at-build-time=org.apache.logging.log4j.util.SortedArrayStringMap$1 ^
  --no-fallback ^
  -H:+ReportExceptionStackTraces ^
  -H:ReflectionConfigurationFiles=conf/reflect-config.json ^
  -jar target/authproxy.jar 2>&1 | .\tee\tee.exe native-build.log
