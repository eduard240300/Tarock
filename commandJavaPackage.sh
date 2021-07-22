#!/bin/bash

jpackage --name "Name Package" --input . --main-jar name.jar --jlink-options --bind-services
