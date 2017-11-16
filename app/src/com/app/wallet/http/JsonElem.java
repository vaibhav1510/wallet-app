package com.app.wallet.http;

import org.json.*;
import java.sql.Timestamp;
import java.util.*;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class JsonElem {
    
    public static JsonElem createFromFile(String path) throws Exception {
        String jsonStr = FileUtils.readFileToString(new File(path));
        return new JsonElem(new JSONObject(jsonStr));
    }
    
    private JSONObject jobj;

    public JsonElem(String _json) throws Exception {
        this(new JSONObject(_json));
    }
    
    public JsonElem(JSONObject json) {
        this.jobj = json;
    }
    
    // underlying JSONObject instance
    public JSONObject obj() {
        return jobj;
    }
    
    public String str(String key) {
        String val = optStr(key);
        return nullCheck(key, val);
    }
    
    public String removeStr(String key) {
        Object val = obj().remove(key);
        return (String)val;
    }
    
    public Integer integer(String key) {
        Integer val = optInt(key);
        return nullCheck(key, val);
    }
    
    public Long reqLong(String key) {
        Long val = optLong(key);
        return nullCheck(key, val);
    }
    
    public Boolean bool(String key) {
        Boolean val = optBool(key);
        return nullCheck(key, val);
    }
    
    public Timestamp unixTime(String key) {
        Timestamp val = optUnixTime(key);
        return nullCheck(key, val);
    }
    
    public JsonElem node(String key) {
        JsonElem val = optNode(key);
        return nullCheck(key, val);
    }
    
    public List<JsonElem> nodes(String key) {
        List<JsonElem> val = optNodes(key);
        return nullCheck(key, val);
    }
    
    public String optStr(String key) {
        return jobj.optString(key, null);
    }
    
    public Integer optInt(String key) {
        String val = jobj.optString(key, null);
        return val == null ? null : Integer.valueOf(val);
    }

    public Long optLong(String key) {
        String val = jobj.optString(key, null);
        return val == null ? null : Long.valueOf(val);
    }
    
    public Boolean optBool(String key) {
        String val = jobj.optString(key, null);
        return val == null ? null : Boolean.valueOf(val);
    }
    
    public Timestamp optUnixTime(String key) {
        Integer val = jobj.optInt(key);
        return val == null ? null : new Timestamp(val * 1000l);
    }
    
    public JsonElem optNode(String key) {
        JSONObject obj = jobj.optJSONObject(key);
        return obj == null ? null : new JsonElem(obj);
    }
    
    public List<JsonElem> optNodes(String key) {
        JSONArray arr = jobj.optJSONArray(key);
        if(arr == null) {
            return null;
        }
        List<JsonElem> toRet = new ArrayList<>(); 
        for (int i = 0; i < arr.length(); i++) {
            try {
                toRet.add(new JsonElem(arr.getJSONObject(i)));
            } catch(JSONException exp) {
                throw new RuntimeException("testing");
//                asRtExp(exp);
            }
        }
        return toRet;
    }
    
    public List<JsonElem> removeNodes(String key) {
        List<JsonElem> toRet = optNodes(key);
        obj().remove(key);
        return toRet == null ? Collections.EMPTY_LIST : toRet;
    }
    
    public List<String> optStrList(String key) {
        JSONArray arr = jobj.optJSONArray(key);
        if(arr == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> toRet = new ArrayList<>(); 
        for (int i = 0; i < arr.length(); i++) {
            try {
                toRet.add(arr.getString(i));
            } catch(JSONException exp) {
            		throw new RuntimeException("testing");
//                throw asRtExp(exp);
            }
        }
        return toRet;
    }
    
    public List<String> keys() {
        JSONArray arr = obj().names();
        return arr == null ? Collections.EMPTY_LIST : arr.list();
    }
    
    public Set<Map.Entry<String, Object>> entrySet() {
        return obj().map().entrySet();
    }
    
    private <T> T nullCheck(String key,T val) {
        if(val == null){
            throw new RuntimeException("The property [" + key + "] is not present ");
        }
        return val;
    }

    @Override
    public String toString() {
        return toString(2);
    }
    
    public String toString(int indent) {
        try {
            return jobj.toString(indent);
        } catch(JSONException exp) {
        		throw new RuntimeException("testing");
//            throw asRtExp(exp);
        }
    }
}
