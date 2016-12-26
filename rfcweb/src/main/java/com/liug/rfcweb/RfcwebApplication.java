package com.liug.rfcweb;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.liug.rfcweb.healthcheck.RfcwebHealthCheck;
import com.liug.rfcweb.rest.RfcwebRest;
import com.liug.rfcweb.service.RfcService;
import com.liug.rfcweb.service.TextMaker;

public class RfcwebApplication extends Application<RfcwebConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(RfcwebApplication.class);

    private RfcService rfcService;
    private TextMaker textMaker;
    private RfcwebRest rest;

    public static void main(String[] args) throws Exception {
        new RfcwebApplication().run(args);
    }

    @Override
    public String getName() {
        return "rfcweb";
    }

    @Override
    public void initialize(Bootstrap<RfcwebConfiguration> bootstrap) {
        super.initialize(bootstrap);
        // 静态网页服务，将resource/static目录挂载在网站根目录下
        bootstrap.addBundle(new AssetsBundle("/static/", "/", "index.html"));

        rfcService = new RfcService();
        textMaker = new TextMaker(rfcService);
        rest = new RfcwebRest(rfcService, textMaker);
    }

    @Override
    public void run(RfcwebConfiguration configuration, Environment environment) {
        textMaker.start();
        environment.jersey().register(rest);
        environment.healthChecks().register("rfcweb", new RfcwebHealthCheck());
    }

}
