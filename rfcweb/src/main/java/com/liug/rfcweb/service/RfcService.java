package com.liug.rfcweb.service;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liug.rfcweb.entity.RfcText;

//@Service
public class RfcService {
    private static final Logger logger = LoggerFactory.getLogger(RfcService.class);

    private ConcurrentMap<String, RfcText> rfcTexts; // key is the filename without postfix

    public RfcService() {
        rfcTexts = new ConcurrentHashMap<String, RfcText>();
    }

    public RfcText getText(String key) {
        if (key == null) {
            return null;
        }
        RfcText text = rfcTexts.get(key);
        if (text != null) {
            return text;
        }

        return text;
    }

    private void createText(String filename) {
    }
}
