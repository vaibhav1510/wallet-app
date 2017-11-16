package com.app.wallet.http;

public class Param<T> {

    private String groupName;

    private String paramName;

    //public final Class<T> dataType;

    public Param(String paramName) {
        this.paramName = paramName;
    }

    public Param(String groupName, String paramName) {
        this.groupName = groupName;
        this.paramName = paramName;
    }

    public String groupName() {
        return groupName;
    }

    public String paramName() {
        return paramName;
    }
}
