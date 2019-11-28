package com.casasolarctpi.prosolar2.models;

public class UserData {

    private String correo, contrasena1, contrasena2, primerNombre, segundoNombre, primerApellido, segundoApellido, institucion, pais, departamento, ciudad, tipodeuso;

    public UserData() {
    }

    public UserData(String correo, String contrasena1, String contrasena2, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String institucion, String pais, String departamento, String ciudad, String tipodeuso) {
        this.correo = correo;
        this.contrasena1 = contrasena1;
        this.contrasena2 = contrasena2;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.institucion = institucion;
        this.pais = pais;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.tipodeuso = tipodeuso;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena1() {
        return contrasena1;
    }

    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    public String getContrasena2() {
        return contrasena2;
    }

    public void setContrasena2(String contrasena2) {
        this.contrasena2 = contrasena2;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
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

    public String getTipodeuso() {
        return tipodeuso;
    }

    public void setTipodeuso(String tipodeuso) {
        this.tipodeuso = tipodeuso;
    }
}
