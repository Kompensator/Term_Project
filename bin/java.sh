#/bin/bash

source=$1".java"
class=$1
echo Compiling $source
`javac $source`
echo Executing $class
`java $class`


