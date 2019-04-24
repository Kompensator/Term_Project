#! /bin/bash

echo Compiling
javac -d . Body.java
javac VerletCollision.java

echo Running Simulation
java VerletCollision

echo Animating...
python animate_collision.py
