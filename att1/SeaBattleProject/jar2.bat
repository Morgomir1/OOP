@echo off

set CD=%~dp0
set mainPath=main\java
set binPath=%CD%bin

set field=%mainPath%\field\*.class
set ship=%mainPath%\ship\*.class
set game=%mainPath%\game\*.class
set Main=%mainPath%\*.class

cd %binPath%
jar cvfe ../SeaBattle.jar main.java.Main %field% %ship% %game% %Main%