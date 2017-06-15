# minishift-hello

Two microservices using Spring Boot and WildFly Swarm with Apache Camel running in MiniShift

There are three Maven projects:

* client - Spring Boot application with Camel that triggers every 2nd second to call the hello service and log the response. The client uses Camel client side retry for error handling. 
* client-hystrix - A client that uses Hystrix as circuit breaker for error handling.
* helloswarm - WildFly Swarm application hostin a hello service which returns a reply message.

The diagram below illustrates this:

![Overview](diagram.png?raw=true "Overview")


### Running MiniShift locally

The applications can be deployed in a OpenShift cluster. You can run a OpenShift cluster locally using MiniShift which you can find more details here: [MiniShift](https://www.openshift.org/minishift/)

### Slides

This source code is used for a talk at various conferences, and you can find the slides for the talks in the [slides](slides) directory.

### Video

I gave a talk `Developing cloud-ready Camel microservices` at Red Hat Summit 2017 which was video recorded and posted on [youtube](https://www.youtube.com/watch?v=a0DXIspd1Zs&index=7&list=PLEGSLwUsxfEh4TE2GDU4oygCB-tmShkSn)

### Prepare shell

When using Maven tooling you want to setup your command shell for docker/kubernetes which can be done by

    minishift docker-env

Which tells you how to setup using eval

    eval $(minishift docker-env)


### Deploying WildFly Swarm (server)

You can deploy the WildFly Swarm application which hosts the hello service.

    cd helloswarm
    mvn install

If the build is success you can deploy to kubernetes using:

    mvn fabric8:deploy


### Deploying Spring Boot (client)

You can deploy the Spring Boot application which is the client calling the hello service

    cd client
    mvn install

If the build is success you can deploy to kubernetes using:

    mvn fabric8:deploy

You should then be able to show the logs of the client, by running `oc get pods` and find the name of the pod that runs the client, and then use `oc logs -f pod-name` to follow the logs.

However you can also run the application from the shell and have logs automatic tailed using

    mvn fabric8:run

And then when you press `cltr + c` then the application is undeployed. This allows to quickly run an application and stop it easily as if you are using `mvn spring-boot:run` or `mvn wildfly-swarm:run` etc.


### Installing Hystrix Dashboard

The `client-hystrix` application which uses Hystrix can be viewed from the Hystrix Dashboard.

To install the dashboard you first need to install a hystrix stat collector which is called Turbine:

    oc create -f http://repo1.maven.org/maven2/io/fabric8/kubeflix/turbine-server/1.0.28/turbine-server-1.0.28-openshift.yml
    oc policy add-role-to-user admin system:serviceaccount:myproject:turbine
    oc expose service turbine-server

Notice that the project name used for adding the policy is `mypolicy`, if you use a different project name, the adjust accordingly.

Then you can install the Hystrix Dashboard:

    oc create -f http://repo1.maven.org/maven2/io/fabric8/kubeflix/hystrix-dashboard/1.0.28/hystrix-dashboard-1.0.28-openshift.yml
    oc expose service hystrix-dashboard --port=8080

You should then be able to open the Hystrix Dashboard if you go into the OpenShift web console and click the url link
of the deployment, something like (http://hystrix-dashboard-myproject.192.168.64.3.nip.io/)

![HystrixDashboard](hystrix-dashboard.png?raw=true "Hystrix Dashboard")

Credits to Charles Moulliard for instructions: https://github.com/redhat-microservices/lab_springboot-openshift/#5-enable-circuit-breaker
