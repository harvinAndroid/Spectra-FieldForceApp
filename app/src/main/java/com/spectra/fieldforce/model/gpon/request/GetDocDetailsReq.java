package com.spectra.fieldforce.model.gpon.request;

public class GetDocDetailsReq {
       private String Action;
       private String Authkey;
       private String wcrId;
       private String irId;

       public GetDocDetailsReq(String action, String authkey, String wcrId, String irId) {
              Action = action;
              Authkey = authkey;
              this.wcrId = wcrId;
              this.irId = irId;
       }
}
