#!/bin/sh

kubectl scale deployment client --replicas=0
kubectl scale deployment client-hystrix --replicas=1

kubectl scale deployment turbine-server --replicas=1
kubectl scale deployment hystrix-dashboard --replicas=1
