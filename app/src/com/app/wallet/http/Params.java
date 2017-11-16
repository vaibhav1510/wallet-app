package com.app.wallet.http;
import java.util.*;

public class Params {
    public enum TT {
        IN_TRIAL, ACTIVE, NON_RENEWING, CANCELLED;
    }

    private Map<String, String> map = new HashMap<String, String>();

    public <T> Params addParam(String name, String value) {
        map.put(name, value);
        return this;
    }

    // used for specifying list inputs. To add a key-value pair with specified index to the list.
    public <T> Params addParamAt(Param param, String value, int index) {
        _assert(param != null, "Param cannot be null");
        _assert(value != null, "Value cannot be null");
        map.put(toKeyStr(param, index), value);
        return this;
    }
    
    public Params param(String name, Object value) {
        _assert(name != null, "Param cannot be null");
        _assert(value != null, "Value cannot be null");
        map.put(name, value.toString());
        return this;
    }
    
    
    public Params optParam(String name, Object value){
        _assert(name != null, "Param cannot be null");
        if(value != null){
            map.put(name, value.toString());
        }
        return this;
    }
    
    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Map<String,String> map() {
        return map;
    }

    private static String toKeyStr(Param param) {
        return param.groupName() != null ? param.groupName() + "[" + param.paramName() + "]"
                : param.paramName();
    }

    // used for list-entry params
    private static String toKeyStr(Param param, int index) {
        return toKeyStr(param) + "[" + String.valueOf(index) + "]";
    }

    private static void _assert(boolean expr, String errMsg) {
        if(!expr) {
            throw new RuntimeException(errMsg);
        }
    }
}