#!/bin/bash

input="./pid1.info"
while IFS= read -r line
do
  kill -9 "$line"
done < "$input"

input="./pid2.info"
while IFS= read -r line
do
  kill -9 "$line"
done < "$input"

input="./pid3.info"
while IFS= read -r line
do
  kill -9 "$line"
done < "$input"

input="./pid4.info"
while IFS= read -r line
do
  kill -9 "$line"
done < "$input"
