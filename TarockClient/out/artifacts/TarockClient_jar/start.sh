#!/bin/bash
java -jar TarockClient.jar eduard 0000 6 & echo $! > ./pid1.info
sleep 0.3
java -jar TarockClient.jar isabella 0000 6 & echo $! > ./pid2.info
sleep 0.3
java -jar TarockClient.jar cezara 0000 6 & echo $! > ./pid3.info
sleep 0.3
java -jar TarockClient.jar denisa 0000 6 & echo $! > ./pid4.info
