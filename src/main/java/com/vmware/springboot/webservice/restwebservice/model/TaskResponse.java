package com.vmware.springboot.webservice.restwebservice.model;

import java.io.Serializable;

public class TaskResponse  implements Serializable {
    private String result;

    public TaskResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TaskResponse{" +
                "result='" + result + '\'' +
                '}';
    }
}
