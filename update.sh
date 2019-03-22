#!/bin/bash

cd ..;
cd local-weaver;
mvn clean install;
cd ..;
cd global-weaver;

cd gw-data;
mvn clean install;
cd ..;
cd gw-generator;
mvn clean install;
cd ..;
cd gw-harvester;
mvn clean install;
cd ..;

