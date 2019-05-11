#! /bin/bash

echo Compiling
javac VerletCollision.java

echo Running Simulation
java VerletCollision

echo Animating...
python animate_collision.py
