package com.liug.rfcweb.service;

import java.util.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RfcService {
    private static final Logger logger = LoggerFactory.getLogger(RfcService.class);

    private ConcurrentMap<String, RfcText> rfcTexts; // key is the filename without postfix

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
