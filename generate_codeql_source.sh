#!/bin/bash

SOURCE_DIR="codeql_source"

echo "create $SOURCE_DIR directory"
if [ -d "$SOURCE_DIR" ]; then
    rm -rf $SOURCE_DIR
fi
mkdir $SOURCE_DIR

echo "copy sources in $SOURCE_DIR"
cp -r src $SOURCE_DIR
cp pom.xml $SOURCE_DIR

cd $SOURCE_DIR || exit
echo "generated delombok classes"
mvn clean lombok:delombok -Pdlombok
rm -rf src/main/java/*
mv target/generated-sources/delombok/* src/main/java/
rm -rf target

cd ..

echo "fine."
