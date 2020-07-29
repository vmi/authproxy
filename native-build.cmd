@chcp 65001
@call "%ProgramFiles(x86)%\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvars64.bat"
@call "setenv.cmd"
native-image ^
  --allow-incomplete-classpath ^
  --report-unsupported-elements-at-runtime ^
  --no-fallback ^
  -H:+ReportExceptionStackTraces ^
  -H:ReflectionConfigurationFiles=conf/reflect-config.json ^
  -jar target/authproxy.jar
