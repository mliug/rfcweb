package com.liug.rfcweb.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class RfcwebHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
