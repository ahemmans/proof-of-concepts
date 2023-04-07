echo off
rem set JAVA_HOME=C:\Java\jdk1.8.0_152
set JAVA_HOME=C:\Progra~1\Java\jdk1.8.0_45
set PATH=%PATH%;%JAVA_HOME%\bin
set JAR_FILE=Peraton-AIR-PBBA-POC1-0.3.0.jar

C:\Progra~1\Java\jdk1.8.0_45\bin\java.exe -jar %JAR_FILE% -DoldFS=fs23,-DnewFS=fs24,-DformType=standard, --spring.profiles.active=irs
rem java -jar target/%JAR_FILE% -DoldFS=fs23,-DnewFS=fs24,-DformType=electronic, --spring.profiles.active=irs