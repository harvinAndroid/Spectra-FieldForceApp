package com.spectra.fieldforce.listener;
public interface OnLoginFormActivityListener {
    public void performRegister();

    public void performLogin(String email, String name);

    public void performResetpassword();

    public void performLogout();

    public void Login(String email, String name);
}