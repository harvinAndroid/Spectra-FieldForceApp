package com.spectra.fieldforce.model.gpon.request;

public class CreateWcrIrDocReq {
    private String Action;
    private String Authkey;
    private String wcrId;
    private String irId;
    private String fileNameWithExtension;
    private String fileContent;

    public void setAction(String action) {
        Action = action;
    }

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setWcrId(String wcrId) {
        this.wcrId = wcrId;
    }

    public void setIrId(String irId) {
        this.irId = irId;
    }

    public void setFileNameWithExtension(String fileNameWithExtension) {
        this.fileNameWithExtension = fileNameWithExtension;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}


