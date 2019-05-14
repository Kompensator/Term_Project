@echo off

echo compiling
javac NewBody.java
javac SunEarthMoon.java

echo running
java SunEarthMoon 

echo simulating
python animate_SEM.py
