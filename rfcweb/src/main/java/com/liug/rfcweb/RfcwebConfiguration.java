package com.liug.rfcweb;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class RfcwebConfiguration extends Configuration {
    private String repodir;

    @JsonProperty
    public String getRepodir() {
        return repodir;
    }
    
    @JsonProperty
    public void setRepodir( String val ) {
        this.repodir = val;
    }
}
