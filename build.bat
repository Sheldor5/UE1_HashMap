@ECHO OFF
mkdir "build"
dir /s /B src\main\java\*.java > sources.txt
xcopy /s src\main\resources\* build
javac -d ./build @sources.txt
cd build
jar cmvf ../src/main/app/MANIFEST.MF ../HashMap-1.0.0.jar./ *
cd ..
