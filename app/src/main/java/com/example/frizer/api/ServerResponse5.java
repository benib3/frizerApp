package com.example.frizer.api;

import com.example.frizer.SlobodniTermini;

public class ServerResponse5 {
    private int status;
    private SlobodniTermini[] message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SlobodniTermini[] getMessage() {
        return message;
    }

    public void setMessage(SlobodniTermini[] message) {
        this.message = message;
    }
}
