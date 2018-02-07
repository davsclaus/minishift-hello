#!/bin/sh

while :; do
  sleep 0.500;
  curl http://192.168.64.6:31045/hello
  echo "";
done