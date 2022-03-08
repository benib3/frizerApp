package com.example.frizer.api;

import com.example.frizer.TerminModel;

public class ServerResponse4 {
    private int status;
    private TerminModel data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TerminModel getData() {
        return data;
    }

    public void setData(TerminModel tm) {
        this.data = tm;
    }
}
