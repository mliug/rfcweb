package com.liug.rfcweb.service;

import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
//import javax.annotation.PostConstruct;
//import javax.inject.Inject;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.jvnet.hk2.annotations.Service;
import io.dropwizard.Configuration;

import com.liug.rfcweb.util.ErrCode;
import com.liug.rfcweb.entity.RfcLine;
import com.liug.rfcweb.entity.RfcText;
import com.liug.rfcweb.entity.RfcLineType;

//@Service
public class TextMaker extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TextMaker.class);

    private Deque<String> taskQue; // task queue, element is the file name without postfix
//    @Inject
    private RfcService rfcService;

//    @Inject
//	private Configuration configuration;

    public TextMaker(RfcService service) {
        taskQue = new ArrayDeque<String>();
        rfcService = service;
//        init();
    }

//    @PostConstruct
////    public void init() {
////        this.start();
////    }

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
            try {sleep(5000);} catch (Exception ee) {;}
            this.start();
        }
    }

    private void processTask(String filename) {
        logger.info("process {}", filename);
        if (rfcService.hasKey(filename)) {
            logger.info("Text of {} exists, omit this task.", filename);
            return;
        }

        String fileN = filename + ".txt";
        File file = new File(fileN);
        if (!file.exists()) {
            logger.warn("file {} doesn\'t exist", fileN);
            cometdErrorMsg(ErrCode.FILE_NOT_EXIST, fileN);
            return;
        }
        processFile(file, filename);
    }

    private void processFile(File file, String nameNoPostfix) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            RfcText text = new RfcText();
            text.setLines(new ArrayList<RfcLine>());
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(text, line);
            }
            text.setFileName(nameNoPostfix);
            text.setId(makeRfcNo(nameNoPostfix));
            rfcService.addText(nameNoPostfix, text);
        } catch (IOException e) {
            logger.warn("error occured: {}", e.getMessage());
            cometdErrorMsg(ErrCode.PROCESSFILE_IOE, e.getMessage());
        } finally {
            if (reader != null) {
                try {reader.close();} catch (IOException ee) {;}
            }
        }
    }

    private void processLine(RfcText text, String line) {
        List<RfcLine> lines = text.getLines();
        RfcLine lastLine = (lines.size() == 0) ? null : lines.get(lines.size() - 1);
        RfcLine newLine = new RfcLine();

        if (line == null || line.length() == 0) { // empty lines
            if (lastLine != null && lastLine.getType() == RfcLineType.EMPTYLINE) {
                lastLine.setLevel((byte)(lastLine.getLevel() + 1));
                return;
            }
            newLine.setType(RfcLineType.EMPTYLINE);
            newLine.setText(null);
            newLine.setLevel((byte)1);
        } else if (line.charAt(0) == ' ') { // the text
            newLine.setType(RfcLineType.TEXT);
            newLine.setText(line);
        } else if (line.length() == 1 && line.charAt(0) == '\f') { // \f
            if (lastLine != null && lastLine.getType() == RfcLineType.TITLE) {
                lastLine.setType(RfcLineType.HEADER);
            }
            newLine.setType(RfcLineType.SEPARATOR);
            newLine.setText(null);
        } else { // the title
            if (lastLine != null && lastLine.getType() == RfcLineType.SEPARATOR) {
                newLine.setType(RfcLineType.FOOTER);
            } else {
                newLine.setType(RfcLineType.TITLE);
            }
            newLine.setText(line);
            newLine.setLevel(calculateLevle(line));
        }

        lines.add(newLine);
    }

    private int makeRfcNo(String name) {
        int rfcNo;
        try {
            rfcNo = Integer.parseInt(name);
        } catch (Exception e) { rfcNo = 0; }

        return rfcNo;
    }

    private byte calculateLevle(String line) {
        // count the number of '.' in the first word as the value of level
        byte level = 1;
        String[] words = line.split(" ");
        for (String w : words) {
            if ( w.length() == 0 || w.equals("Appendix")) {
                continue;
            }
            byte[] bs = w.getBytes();
            level = 0;
            for (byte b : bs) {
                level += (b == (byte)0x2e) ? 1 : 0;
            }
            level = (level == 0) ? 1 : level;
            break;
        }
        return level;
    }

    private void cometdErrorMsg(int errCode, String description) {
        //TODO:
    }

}
