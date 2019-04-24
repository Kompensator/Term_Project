#! /bin/bash

echo Compiling
javac Verlet.java
echo Running Simulation
java Verlet
echo Animating...
python animate.py
