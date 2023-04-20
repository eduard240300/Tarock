#!/bin/bash

cd ..

cd "Releases/DEB-for-Client/tarock-client_1.0-1_amd64/opt/tarock-client/lib/app"
rm -rf TarockClient.jar
cp -R  ../../../../../../../Tarock/out/artifacts/TarockClient_jar/TarockClient.jar ./TarockClient.jar

cd "../../../../../../../"

cd "Releases/DEB-for-Server/tarock-server_1.0-1_amd64/opt/tarock-server/lib/app"
rm -rf TarockServer.jar
cp -R  ../../../../../../../Tarock/out/artifacts/TarockServer_jar/TarockServer.jar ./TarockServer.jar

cd "../../../../../../../"

cd "Releases/DEB-for-Client/"
rm -rf tarock-client_1.0-1_amd64.deb
sudo dpkg-deb --build --root-owner-group ./tarock-client_1.0-1_amd64
sudo chown build:build ./tarock-client_1.0-1_amd64.deb

cd "../../"

cd "Releases/DEB-for-Server/"
rm -rf tarock-server_1.0-1_amd64.deb
sudo dpkg-deb --build --root-owner-group ./tarock-server_1.0-1_amd64
sudo chown build:build ./tarock-server_1.0-1_amd64.deb
