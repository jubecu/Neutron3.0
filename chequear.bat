set CLASSPATH=.\lib\log4j-1.2.17.jar;.\lib\slf4j-api-1.7.1.jar;.lib\slf4j-log4j12-1.7.1.jar;.\rsc\log4j.properties;.\src
mkdir doccheck
javadoc -doclet com.sun.tools.doclets.doccheck.DocCheck ^
-docletpath .\lib\doccheck.jar ^
-d .\doccheck ^
-encoding UTF-8 ^
-private ^
-subpackages juego