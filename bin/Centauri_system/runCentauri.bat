@echo off

echo compiling
javac Body.java
javac Centauri.java

echo running
java Centauri

echo simulating
python animate_centauri.py
