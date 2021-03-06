package com.liug.rfcweb.entity;

public class RfcLine {
    private RfcLineType type;
    private String text;
    // when type is TITLE, level is the level of title
    // when type is EMPTYLINE, level is the number of empty lines.
    private byte level;

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
    public RfcLine(RfcLineType t, String _text, byte l) {
        type = t;
        text = _text;
        level = l;
    }
    public RfcLine() {}
}
