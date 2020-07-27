package com.spectra.fieldforce;
public interface OnLoginFormActivityListener {
    public void performRegister();

    public void performLogin(String email, String name);

    public void performResetpassword();

    public void performLogout();
}