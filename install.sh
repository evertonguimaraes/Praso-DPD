#!/bin/bash

# Ensure we're running as root user
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

# If Java is not installed, install it
if [ -n `which java` ]; then
  sudo apt-get install openjdk-8-jdk
fi

# Install SBT, which automatically installs Scala
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt

# Install ammonite-scripts, ammonite-ops and ammonite-shell
mkdir ~/.ammonite
cp ./project/predef* ~/.ammonite