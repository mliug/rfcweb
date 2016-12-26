package com.liug.rfcweb.rest;

import com.liug.rfcweb.entity.RfcText;
import com.liug.rfcweb.entity.RfcwebException;
import com.liug.rfcweb.service.RfcService;
import com.liug.rfcweb.service.TextMaker;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
//import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/rfcweb")
@Produces(MediaType.APPLICATION_JSON)
public class RfcwebRest {
    private static final Logger logger = LoggerFactory.getLogger(RfcwebRest.class);

//    @Inject
    private RfcService service;
    private TextMaker textMaker;

    public RfcwebRest(RfcService s, TextMaker maker) {
        service = s;
        textMaker = maker;
    }

    @GET
    @Path("/file/{name}")
    public RfcText queryText(@PathParam("name") String name) {
        try {
            RfcText text = service.getText(name);
            if (null == text) {
                textMaker.addTask(name);
            }
            return text;
        } catch (RfcwebException e) {
            logger.warn("errCode: {}, description: {}",
                    e.getErrCode(), e.getMessage());
            return null;
        }
    }
}
