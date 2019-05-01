@echo off

echo compiling
javac -d . Body.java
javac SunEarthMoon.java

echo running
java SunEarthMoon

echo simulating
python animate_collision.py