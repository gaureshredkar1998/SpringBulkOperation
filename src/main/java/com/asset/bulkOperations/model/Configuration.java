package com.asset.bulkOperations.model;

public class Configuration {

    int numberOfThreads;

    long requestPerThread;

    long sleepTimeInSeconds;

    String httpErrorCode = "503";

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public long getRequestPerThread() {
        return requestPerThread;
    }

    public void setRequestPerThread(long requestPerThread) {
        this.requestPerThread = requestPerThread;
    }

    public long getSleepTimeInSeconds() {
        return sleepTimeInSeconds;
    }

    public void setSleepTimeInSeconds(long sleepTimeInSeconds) {
        this.sleepTimeInSeconds = sleepTimeInSeconds;
    }

    public String getHttpErrorCode() {
        return httpErrorCode;
    }

    public void setHttpErrorCode(String httpErrorCode) {
        this.httpErrorCode = httpErrorCode;
    }
}
