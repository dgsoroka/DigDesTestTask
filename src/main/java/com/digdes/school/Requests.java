package com.digdes.school;

public enum Requests {
    INSERT ("insert values"),
    UPDATE ("update values"),
    DELETE ("delete"),
    SELECT ("select");

    private String request;
    Requests(String request) {
        this.request = request;
    }

    @Override
    public String toString(){
        return request;
    }
}
