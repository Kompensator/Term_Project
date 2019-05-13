#! /bin/bash

echo Compiling
javac Binaries.java

echo Running Simulation
java Binaries

echo Animating...
python animate_binary.py
