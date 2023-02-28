package com.reportbuilder.model;

public enum TypeOfData {
    varchar, VARCHAR, CHARACTER, VARYING,
    int4, integer, INTEGER;

    @Override
    public String toString() {
        return name();
    }

    public static String getCharacterVarying() {
        return CHARACTER + " " + VARYING;
    }
}
