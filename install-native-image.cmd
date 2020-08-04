@chcp 65001
@call "%ProgramFiles(x86)%\Microsoft Visual Studio\2019\Community\VC\Auxiliary\Build\vcvars64.bat"
@call "setenv.cmd"
gu install native-image
