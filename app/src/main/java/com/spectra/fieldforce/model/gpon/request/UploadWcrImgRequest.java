package com.spectra.fieldforce.model.gpon.request;

public class UploadWcrImgRequest {
    public String Action,Authkey,WCRguidId,LanFileContent,LanFileName,WifiFileContent,WifiFileName;

    public UploadWcrImgRequest(String action, String authkey, String WCRguidId, String lanFileContent, String lanFileName, String wifiFileContent, String wifiFileName) {
        Action = action;
        Authkey = authkey;
        this.WCRguidId = WCRguidId;
        LanFileContent = lanFileContent;
        LanFileName = lanFileName;
        WifiFileContent = wifiFileContent;
        WifiFileName = wifiFileName;
    }
}
