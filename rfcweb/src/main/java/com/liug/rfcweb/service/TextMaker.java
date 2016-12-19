package com.liug.rfcweb.service;

import java.util.Deque;
import java.util.ArrayDeque;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jvnet.hk2.annotations.Service;

@Service
public class TextMaker extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TextMaker.class);

    private Deque<String> taskQue; // task queue, element is the file name without postfix
    @Inject
    private RfcService rfcService;

    public TextMaker() {
        taskQue = new ArrayDeque<String>();
    }

    @PostConstruct
    private void init() {
        this.start();
    }

    public synchronized boolean addTask(String filename) {
        if (filename == null || filename.length() == 0 || taskQue.contains(filename)) {
            return false;
        }
        logger.info("add task: {}", filename);
        taskQue.addLast(filename);
        return true;
    }

    private synchronized String popTask() {
        return taskQue.pollFirst();
    }

    private synchronized int taskSize() {
        return taskQue.size();
    }

    @Override
    public void run() {
        logger.info("TextMaker ready to process task.");
        try {
            String filename;
            while (true) {
                filename = popTask();
                if (filename != null) {
                    processTask(filename);
                    if (taskSize() > 0) {
                        continue;
                    }
                }
                sleep(300);
            }
        } catch (Exception e) {
            logger.warn("Caught Exception: {}, thread will restart after 5 seconds.",
                    e.getMessage());
            try {sleep(5000);} catch (Exception e) {;}
            this.start();
        }
    }

    private void processTask(filename) {
    }

}
