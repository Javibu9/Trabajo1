package com.example.trabajo1.Models;

public class Login {
    private String email;
    private String contraseña;

    public Login(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
