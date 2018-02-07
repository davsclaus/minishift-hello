#!/bin/sh

while :; do
  sleep 0.500;
  curl http://192.168.64.5:30605/hello
  echo "";
done