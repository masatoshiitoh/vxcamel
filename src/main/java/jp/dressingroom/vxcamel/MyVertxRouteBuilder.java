package jp.dressingroom.vxcamel;

import io.vertx.core.impl.VertxBuilder;
import io.vertx.core.impl.transports.JDKTransport;
import io.vertx.core.spi.ExecutorServiceFactory;
import io.vertx.core.spi.VertxThreadFactory;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.vertx.VertxComponent;

public class MyVertxRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        VertxComponent vertxComponent = new VertxComponent();
        vertxComponent.setVertxFactory(
                new VertxBuilder()
                        .threadFactory(VertxThreadFactory.INSTANCE)
                        .findTransport(new JDKTransport())
                        .executorServiceFactory(ExecutorServiceFactory.INSTANCE)
                        .clusterManager(new HazelcastClusterManager())
        );
        vertxComponent.setHost("127.0.0.1");
        getCamelContext().addComponent("vertx", vertxComponent);
        System.out.println("vertxComponent.getHost(): " + vertxComponent.getHost());

        from("vertx:out").log("eventbus").to("file:target/vertx");
    }
}
