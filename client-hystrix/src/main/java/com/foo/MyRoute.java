package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Uses the Hystrix Circuit Breaker
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



