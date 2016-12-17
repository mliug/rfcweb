package com.liug.rfcweb.entity;

public class RfcLine {
    private RfcLineType type;
    private String text;
    private byte level;
    private int mark;

    public int getMark() {
        return mark;
    }
    
    public void setMark( int val ) {
        this.mark = val;
    }

    public byte getLevel() {
        return level;
    }
    
    public void setLevel( byte val ) {
        this.level = val;
    }

    public String getText() {
        return text;
    }
    
    public void setText( String val ) {
        this.text = val;
    }

    public RfcLineType getType() {
        return type;
    }
    
    public void setType( RfcLineType val ) {
        this.type = val;
    }
    public RfcLine(RfcLineType t, String _text, byte l, int _mark) {
        type = t;
        text = _text;
        level = l;
        mark = _mark;
    }
    public RfcLine() {}
}
