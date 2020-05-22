package com.vmware.springboot.webservice.restwebservice.model;


import java.io.Serializable;

public class TaskRequest implements Serializable {
    private  Integer Goal;
    private  Integer Step;

    public TaskRequest() {
    }

    public Integer getGoal() {
        return Goal;
    }

    @Override
    public String toString() {
        return "SampleRequest{" +
                "Goal=" + Goal +
                ", Step=" + Step +
                '}';
    }

    public void setGoal(Integer goal) {
        Goal = goal;
    }

    public Integer getStep() {
        return Step;
    }

    public void setStep(Integer step) {
        Step = step;
    }
}
