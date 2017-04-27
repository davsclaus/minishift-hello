package com.foo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Use Camel error handler to perform redelivery when calling the service fails
 */
@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // try to call the service again
//        onException(Exception.class)
//                .maximumRedeliveries(10)
//                .redeliveryDelay(1000);

        from("timer:foo?period=2000")
                .to("http4:{{service:helloswarm}}/hello?connectionClose=true")
                .log("${body}");
    }
}



