package com.liug.rfcweb.entity;

import java.util.List;

public class RfcText {
    private String fileName;
    private int id;
    private List<RfcLine> lines;

    public List<RfcLine> getLines() {
        return lines;
    }
    
    public void setLines( List<RfcLine> val ) {
        this.lines = val;
    }

    public int getId() {
        return id;
    }
    
    public void setId( int val ) {
        this.id = val;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void setFileName( String val ) {
        this.fileName = val;
    }
}
