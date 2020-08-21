package com.spectra.fieldforce.Model;

import java.util.ArrayList;
import java.util.List;

public class ChnageBinResponse {

    private Integer Status;
    private ArrayList<Response> Response ;
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public ArrayList<Response> getResponse() {
        return Response;
    }


    public void setResponse(ArrayList<Response> Response) {
        this.Response = Response;
    }


public class Response {

    private String binId;
    private String teamName;

    public String getBinId() {
        return binId;
    }

    public void setBinId(String binId) {
        this.binId = binId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

}
}