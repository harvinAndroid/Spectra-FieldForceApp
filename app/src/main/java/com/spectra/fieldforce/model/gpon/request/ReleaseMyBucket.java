package com.spectra.fieldforce.model.gpon.request;

public class ReleaseMyBucket {
    private String  Authkey,Action,OrderId,OrderType,CustomerId;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }
}
