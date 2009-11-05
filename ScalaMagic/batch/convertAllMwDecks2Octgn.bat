@echo off
REM converts all deck files from the argument folder from MWS format to OCTGN

set SCALA_LIB=lib\scala-library.jar
set OCTGN_LIB=mws2octgn.jar
set MAIN_CLASS=com.tyrcho.magic.octgn.Otcgn2DeckImporter 

java -cp %OCTGN_LIB%;%SCALA_LIB% %MAIN_CLASS% %1 data\sets.xml