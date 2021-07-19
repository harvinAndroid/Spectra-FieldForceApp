package com.spectra.fieldforce.model.gpon.request;

public class GetINSRequest {
    private String Action,Authkey,ONTSerialNo;

    public GetINSRequest(String action, String authkey, String ONTSerialNo) {
        Action = action;
        Authkey = authkey;
        this.ONTSerialNo = ONTSerialNo;
    }
}
