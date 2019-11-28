package com.casasolarctpi.prosolar2.models;

public class DatosTiempoReal {

    public String corriente1, corriente2, corriente3, corriente4, voltaje1, voltaje2, voltaje3, voltaje4, hora, humedad, irradiancia, temperatura, fechaActual, fechaActual1;


    public DatosTiempoReal() {
    }

    public DatosTiempoReal(String corriente1, String corriente2, String corriente3, String corriente4, String voltaje1, String voltaje2, String voltaje3, String voltaje4, String hora, String humedad, String irradiancia, String temperatura, String fechaActual, String fechaActual1) {
        this.corriente1 = corriente1;
        this.corriente2 = corriente2;
        this.corriente3 = corriente3;
        this.corriente4 = corriente4;
        this.voltaje1 = voltaje1;
        this.voltaje2 = voltaje2;
        this.voltaje3 = voltaje3;
        this.voltaje4 = voltaje4;
        this.hora = hora;
        this.humedad = humedad;
        this.irradiancia = irradiancia;
        this.temperatura = temperatura;
        this.fechaActual = fechaActual;
        this.fechaActual1 = fechaActual1;
    }

    public String getCorriente1() {
        return corriente1;
    }

    public void setCorriente1(String corriente1) {
        this.corriente1 = corriente1;
    }

    public String getCorriente2() {
        return corriente2;
    }

    public void setCorriente2(String corriente2) {
        this.corriente2 = corriente2;
    }

    public String getCorriente3() {
        return corriente3;
    }

    public void setCorriente3(String corriente3) {
        this.corriente3 = corriente3;
    }

    public String getCorriente4() {
        return corriente4;
    }

    public void setCorriente4(String corriente4) {
        this.corriente4 = corriente4;
    }

    public String getVoltaje1() {
        return voltaje1;
    }

    public void setVoltaje1(String voltaje1) {
        this.voltaje1 = voltaje1;
    }

    public String getVoltaje2() {
        return voltaje2;
    }

    public void setVoltaje2(String voltaje2) {
        this.voltaje2 = voltaje2;
    }

    public String getVoltaje3() {
        return voltaje3;
    }

    public void setVoltaje3(String voltaje3) {
        this.voltaje3 = voltaje3;
    }

    public String getVoltaje4() {
        return voltaje4;
    }

    public void setVoltaje4(String voltaje4) {
        this.voltaje4 = voltaje4;
    }

    public String getHora() {
        return hora;
    }

    public String setHora(String hora) {
        this.hora = hora;
        return hora;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getIrradiancia() {
        return irradiancia;
    }

    public void setIrradiancia(String irradiancia) {
        this.irradiancia = irradiancia;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getFechaActual1() {
        return fechaActual1;
    }

    public void setFechaActual1(String fechaActual1) {
        this.fechaActual1 = fechaActual1;
    }
}
