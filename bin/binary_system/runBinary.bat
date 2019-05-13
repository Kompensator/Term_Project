@echo off

echo compiling
javac NewBody.java
javac binaries.java

echo running
java binaries

echo simulating
python animate_binary.py