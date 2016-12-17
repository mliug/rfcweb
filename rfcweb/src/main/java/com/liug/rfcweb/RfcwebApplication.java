package com.liug.rfcweb;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
//import com.example.helloworld.resources.HelloWorldResource;
//import com.example.helloworld.health.TemplateHealthCheck;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.liug.rfcweb.healthcheck.RfcwebHealthCheck;

public class RfcwebApplication extends Application<RfcwebConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(RfcwebApplication.class);

    public static void main(String[] args) throws Exception {
        new RfcwebApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<RfcwebConfiguration> bootstrap) {
        super.initialize(bootstrap);
        // 静态网页服务，将resource/static目录挂载在网站根目录下
        bootstrap.addBundle(new AssetsBundle("/static/", "/", "index.html"));
    }

    @Override
    public void run(RfcwebConfiguration configuration,
                    Environment environment) {
        // nothing to do yet
        environment.healthChecks().register("rfcweb", new RfcwebHealthCheck());
        logger.info("STARTED!");
    }

}
