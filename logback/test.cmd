@echo OFF
call mvn package install
set CWD=%CD:\=/%
set TARGET=%CD%\target
set SELENIUM_HOME=C:\JAVA\SELENIUM
set SELENIUM_VERSION=2.47.1

REM If multiple logback configurations found, only the last entry matters

java ^
-Dlogback.configurationFile=src/logback.xml ^
-cp target\app-1.1-SNAPSHOT.jar;%SELENIUM_HOME%\selenium-server-standalone-%SELENIUM_VERSION%.jar;target\lib\* ^
com.mycompany.app.App

goto :EOF
REM http://stackoverflow.com/questions/32324043/logback-output-to-mysql
REM http://stackoverflow.com/questions/23879829/logback-dbappender-not-logging
REM https://groups.google.com/forum/#!topic/selenium-users/i_xKZpLfuTk
REM http://www.srccodes.com/p/article/5/Hello-World-Example-of-Simple-Logging-Facade-for-Java-or-SLF4J
REM http://www.srccodes.com/p/article/5/Hello-World-Example-of-Simple-Logging-Facade-for-Java-or-SLF4J
REM  The configuration seems to be ignored 
REM log4j.properties
REM log4j.xml
REM http://stackoverflow.com/questions/17548997/maven-include-resources-into-jar
REM http://stackoverflow.com/questions/4311026/how-to-get-slf4j-hello-world-working-with-log4j
REM When a console warning gives you a URL to look at, and the URL says Knowing the appropriate 
REM location to place log4j.properties or log4j.xml requires 
REM understanding the search strategy of the class loader in use.
