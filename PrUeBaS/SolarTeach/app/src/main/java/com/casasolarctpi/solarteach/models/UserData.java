package com.casasolarctpi.solarteach.models;

public class UserData {

    private String email, primerN, segundoN, primerA, segundoA, tipodeuso, institucion, pais, departamento, ciudad, puntaje;

    public UserData() {
    }

    public UserData(String email, String primerN, String segundoN, String primerA, String segundoA, String tipodeuso, String institucion, String pais, String departamento, String ciudad, String puntaje) {
        this.email = email;
        this.primerN = primerN;
        this.segundoN = segundoN;
        this.primerA = primerA;
        this.segundoA = segundoA;
        this.tipodeuso = tipodeuso;
        this.institucion = institucion;
        this.pais = pais;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.puntaje = puntaje;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimerN() {
        return primerN;
    }

    public void setPrimerN(String primerN) {
        this.primerN = primerN;
    }

    public String getSegundoN() {
        return segundoN;
    }

    public void setSegundoN(String segundoN) {
        this.segundoN = segundoN;
    }

    public String getPrimerA() {
        return primerA;
    }

    public void setPrimerA(String primerA) {
        this.primerA = primerA;
    }

    public String getSegundoA() {
        return segundoA;
    }

    public void setSegundoA(String segundoA) {
        this.segundoA = segundoA;
    }

    public String getTipodeuso() {
        return tipodeuso;
    }

    public void setTipodeuso(String tipodeuso) {
        this.tipodeuso = tipodeuso;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(String puntaje) {
        this.puntaje = puntaje;
    }
}
