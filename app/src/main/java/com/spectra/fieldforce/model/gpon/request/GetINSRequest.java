package com.spectra.fieldforce.model.gpon.request;

public class GetINSRequest {
    private String Action,Authkey,ONTSerialNo,CanId;

    public GetINSRequest(String action, String authkey, String ONTSerialNo, String CanId) {
        Action = action;
        Authkey = authkey;
        CanId = CanId;
        this.ONTSerialNo = ONTSerialNo;

    }
}
