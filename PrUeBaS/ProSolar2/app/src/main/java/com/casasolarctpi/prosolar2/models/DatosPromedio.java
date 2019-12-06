package com.casasolarctpi.prosolar2.models;

public class DatosPromedio {
    private float hora = 0 , temperaturaPromedio = 0, humedadPromedio = 0, corriente1Promedio = 0,corriente2Promedio = 0,corriente3Promedio = 0,corriente4Promedio = 0, irradianciaPromedio = 0, voltaje1Promedio = 0, voltaje2Promedio = 0, voltaje3Promedio = 0, voltaje4Promedio = 0;


    public DatosPromedio() {
    }

    public DatosPromedio(float hora, float temperaturaPromedio, float humedadPromedio, float corriente1Promedio, float corriente2Promedio, float corriente3Promedio, float corriente4Promedio, float irradianciaPromedio, float voltaje1Promedio, float voltaje2Promedio, float voltaje3Promedio, float voltaje4Promedio) {
        this.hora = hora;
        this.temperaturaPromedio = temperaturaPromedio;
        this.humedadPromedio = humedadPromedio;
        this.corriente1Promedio = corriente1Promedio;
        this.corriente2Promedio = corriente2Promedio;
        this.corriente3Promedio = corriente3Promedio;
        this.corriente4Promedio = corriente4Promedio;
        this.irradianciaPromedio = irradianciaPromedio;
        this.voltaje1Promedio = voltaje1Promedio;
        this.voltaje2Promedio = voltaje2Promedio;
        this.voltaje3Promedio = voltaje3Promedio;
        this.voltaje4Promedio = voltaje4Promedio;
    }

    public float getHora() {
        return hora;
    }

    public void setHora(float hora) {
        this.hora = hora;
    }

    public float getTemperaturaPromedio() {
        return temperaturaPromedio;
    }

    public void setTemperaturaPromedio(float temperaturaPromedio) {
        this.temperaturaPromedio = temperaturaPromedio;
    }

    public float getHumedadPromedio() {
        return humedadPromedio;
    }

    public void setHumedadPromedio(float humedadPromedio) {
        this.humedadPromedio = humedadPromedio;
    }

    public float getCorriente1Promedio() {
        return corriente1Promedio;
    }

    public void setCorriente1Promedio(float corriente1Promedio) {
        this.corriente1Promedio = corriente1Promedio;
    }

    public float getCorriente2Promedio() {
        return corriente2Promedio;
    }

    public void setCorriente2Promedio(float corriente2Promedio) {
        this.corriente2Promedio = corriente2Promedio;
    }

    public float getCorriente3Promedio() {
        return corriente3Promedio;
    }

    public void setCorriente3Promedio(float corriente3Promedio) {
        this.corriente3Promedio = corriente3Promedio;
    }

    public float getCorriente4Promedio() {
        return corriente4Promedio;
    }

    public void setCorriente4Promedio(float corriente4Promedio) {
        this.corriente4Promedio = corriente4Promedio;
    }

    public float getIrradianciaPromedio() {
        return irradianciaPromedio;
    }

    public void setIrradianciaPromedio(float irradianciaPromedio) {
        this.irradianciaPromedio = irradianciaPromedio;
    }

    public float getVoltaje1Promedio() {
        return voltaje1Promedio;
    }

    public void setVoltaje1Promedio(float voltaje1Promedio) {
        this.voltaje1Promedio = voltaje1Promedio;
    }

    public float getVoltaje2Promedio() {
        return voltaje2Promedio;
    }

    public void setVoltaje2Promedio(float voltaje2Promedio) {
        this.voltaje2Promedio = voltaje2Promedio;
    }

    public float getVoltaje3Promedio() {
        return voltaje3Promedio;
    }

    public void setVoltaje3Promedio(float voltaje3Promedio) {
        this.voltaje3Promedio = voltaje3Promedio;
    }

    public float getVoltaje4Promedio() {
        return voltaje4Promedio;
    }

    public void setVoltaje4Promedio(float voltaje4Promedio) {
        this.voltaje4Promedio = voltaje4Promedio;
    }
}

