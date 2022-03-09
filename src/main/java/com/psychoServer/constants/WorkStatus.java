package com.psychoServer.constants;

public enum WorkStatus {
    idle("空闲"), busy("忙碌"), left("离开");
    public String status;

    WorkStatus(String r) {
        this.status = r;
    }
}
