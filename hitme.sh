#!/bin/sh

while :; do
  sleep 0.500;
  curl http://192.168.64.5:30555/hello
  echo "";
done