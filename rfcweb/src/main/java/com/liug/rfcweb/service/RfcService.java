package com.liug.rfcweb.service;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.jvnet.hk2.annotations.Service;

import com.liug.rfcweb.entity.RfcText;
import com.liug.rfcweb.entity.RfcwebException;
import com.liug.rfcweb.util.ErrCode;

//@Service
public class RfcService {
    private static final Logger logger = LoggerFactory.getLogger(RfcService.class);

    private ConcurrentMap<String, RfcText> rfcTexts; // key is the filename without postfix

    public RfcService() {
        rfcTexts = new ConcurrentHashMap<String, RfcText>();
    }

    public boolean hasKey(String key) {
        return (key == null) ? false : rfcTexts.containsKey(key);
    }

    public boolean addText(String key, RfcText text) {
        if (key == null || text == null) {
            return false;
        }
        rfcTexts.put(key, text);
        return true;
    }

    public RfcText getText(String key) {
        if (key == null) {
            throw new RfcwebException(ErrCode.NULL_KEY, "key is null");
        }
        RfcText text = rfcTexts.get(key);
//        if (text == null) {
//            throw new RfcwebException(ErrCode.TEXT_NOT_EXIST,
//                    "no text for key(" + key + ")");
//        }
        return text;
    }
}
