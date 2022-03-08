package com.example.frizer.api;

import com.example.frizer.TerminModel;

public class ServerResponse {
    private int status;
    private TerminModel[] message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TerminModel[] getMessage() {
        return message;
    }

    public void setMessage(TerminModel[] tm) {
        this.message = tm;
    }
}
