package com.project.ece651.webapp.entities;

public enum Type {
    APARTMENT, HOUSE;

    // for conversion to json
    // https://stackoverflow.com/questions/18031125/what-is-the-difference-between-enum-name-and-enum-tostring
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
