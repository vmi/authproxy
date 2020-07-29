@chcp 65001
@call "%ProgramFiles(x86)%\Microsoft Visual Studio\2017\BuildTools\VC\Auxiliary\Build\vcvars64.bat" /Release /x64 2> NUL
native-image ^
  --allow-incomplete-classpath ^
  --report-unsupported-elements-at-runtime ^
  --no-fallback ^
  -H:+ReportExceptionStackTraces ^
  -H:ReflectionConfigurationFiles=conf/reflect-config.json ^
  -jar target/authproxy.jar
