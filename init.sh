#!/bin/bash
# Initial setup of VM @ debian

# Stratus.FI template image:
# ID  Name              Owner     Group     Registration time
# 957	Debian 11 [CVTFI]	oneadmin	oneadmin	20/08/2021 13:58:18

# We ssh to the VM as root, so no sudo

set -e

echo '## Update the system'
apt update && sudo apt -y upgrade

echo '## Install Java'
# This should install OpenJDK 11 JDK
apt -y install default-jdk
# Verify the Java version
java -version

echo '## Install Maven'
apt -y install maven
# Verify the Maven version
mvn -version

echo '## Install Docker'
# https://docs.docker.com/engine/install/debian/
# Set up the repository
# apt update
apt -y install ca-certificates curl gnupg lsb-release
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
# Install Docker Engine
apt update
apt -y install docker-ce docker-ce-cli containerd.io
# Verify that Docker Engine is installed correctly
docker run hello-world

echo '## Install Docker Compose'
# https://docs.docker.com/compose/install/
# Install Compose on Linux systems
COMPOSE_VERSION="1.29.2"
echo "## Compose version to install: $COMPOSE_VERSION (might not be the latest)"
sudo curl -L "https://github.com/docker/compose/releases/download/$COMPOSE_VERSION/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
# Verify the Docker Compose version
docker-compose --version

echo '## Run MathMLCanEval'
mvn clean install
docker-compose up

# Everything shold be up and running now
# Open localhost:9080 (or substitute localhost with the VM's IP address)
