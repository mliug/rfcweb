package com.liug.rfcweb.entity;

enum RfcLineType {
    TITLE(1),
    TEXT(2),
    HEADER(3),
    FOOTER(4),
    SEPARATOR(5);

    private RfcLineType(int v) {
        this.value = v;
    }

    private int value;
}
