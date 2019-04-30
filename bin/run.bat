@echo off

echo compiling
javac -d . Body.java
javac VerletCollision.java

echo running
java VerletCollision

echo simulating
python animate_collision.py