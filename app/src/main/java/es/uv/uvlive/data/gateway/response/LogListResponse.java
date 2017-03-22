package es.uv.uvlive.data.gateway.response;

import java.util.ArrayList;

public class LogListResponse {
    private ArrayList<LogResponse> logs;

    public ArrayList<LogResponse> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<LogResponse> logs) {
        this.logs = logs;
    }
}
