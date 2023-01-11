package com.asset.bulkOperations.model;

public class Request {

    HTTPRequest httpRequest;

    Configuration config;



    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public HTTPRequest getHttpRequest() {
        return httpRequest;
    }



    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
}
