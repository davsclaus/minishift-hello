package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Uses the Hystrix Circuit Breaker.
 *
 * See also the <tt>application.properties</tt> file where
 * Hystrix has been configured, and the <tt>src/main/fabric8/service.yml</tt> file
 * which adds a label to instruct the Turbine/Hystrix Dashboard that this
 * container runs Hystrix.
 */
@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo?period=2000")
            .hystrix().id("call-helloswarm")
                .to("http4:{{service:helloswarm}}/hello?connectionClose=true")
            .onFallback()
                .setBody().constant("Nobody want to talk to me")
            .end()
            .log("${body}");
    }
}



