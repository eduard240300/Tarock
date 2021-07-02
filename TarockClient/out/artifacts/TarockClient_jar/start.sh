#!/bin/bash
java -jar TarockClient.jar eduard 0000 1 & echo $! > ./pid1.info
java -jar TarockClient.jar isabella 0000 1 & echo $! > ./pid2.info
java -jar TarockClient.jar cezara 0000 1 & echo $! > ./pid3.info
java -jar TarockClient.jar denisa 0000 1 & echo $! > ./pid4.info
