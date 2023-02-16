package com.reportbuilder.model;

public enum TypeOfData {
    varchar, VARCHAR,
    int4, integer, INTEGER;

    @Override
    public String toString() {
        return name();
    }
}
