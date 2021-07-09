package com.spectra.fieldforce.model.gpon.request;

public class UpdateAppointmentRequest {
    private String Authkey,Action,OrderID,AppointmentDate;

    public void setAuthkey(String authkey) {
        Authkey = authkey;
    }

    public void setAction(String action) {
        Action = action;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }
}
