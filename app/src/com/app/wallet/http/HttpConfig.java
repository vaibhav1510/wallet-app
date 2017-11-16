package com.app.wallet.http;

public class HttpConfig {

    public enum AuthenticationScheme {

        BASIC, BEARER;

    }

    public final String username;

    public final String password;

    public final String contentType; // content mime type

    public final String respType; // accept header

    public int connectTimeout;

    public int readTimeout;

    public AuthenticationScheme authScheme = AuthenticationScheme.BASIC;

    public HttpConfig(String username) {
        this(username, null);
    }

    public HttpConfig(String username, String password) {
        this(username, password, "application/x-www-form-urlencoded", "application/json;charset=utf-8");
    }

    public HttpConfig(String username, String password, String contentType, String respType) {
        this.username = username;
        this.password = password;
        this.contentType = contentType;
        this.respType = respType;
        this.connectTimeout = Integer.getInteger("com.chargebee.api.http.timeout.connect", 15000);
        this.readTimeout = Integer.getInteger("com.chargebee.api.http.timeout.read", 60000);
    }

    public HttpConfig authenticationScheme(AuthenticationScheme authScheme) {
        this.authScheme = authScheme;
        return this;
    }

}
