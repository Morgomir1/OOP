@echo off

set CD=%~dp0
set mainPath=%CD%src\main\java

set field=-d bin %mainPath%\field\*.java
set ship=-d bin %mainPath%\ship\*.java
set game=-d bin %mainPath%\game\*.java
set Main=-d bin %mainPath%\*.java

javac %field% %ship% %game% %Main%