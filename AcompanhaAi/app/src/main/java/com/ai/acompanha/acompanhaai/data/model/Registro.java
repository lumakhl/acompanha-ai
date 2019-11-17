package com.ai.acompanha.acompanhaai.data.model;

import java.util.Date;

public class Registro {

    private long id;
    private int consumo;
    private double valorPrevisto;
    private double valorReal;
    private Date periodo;

    public Registro(long id, int consumo, double valorPrevisto, double valorReal, Date periodo) {
        this.id = id;
        this.consumo = consumo;
        this.valorPrevisto = valorPrevisto;
        this.valorReal = valorReal;
        this.periodo = periodo;
    }

    public Registro(int consumo, double valorPrevisto, double valorReal, Date periodo) {
        this.consumo = consumo;
        this.valorPrevisto = valorPrevisto;
        this.valorReal = valorReal;
        this.periodo = periodo;
    }

    public Registro() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getConsumo() {
        return consumo;
    }

    public void setConsumo(int consumo) {
        this.consumo = consumo;
    }

    public double getValorPrevisto() {
        return valorPrevisto;
    }

    public void setValorPrevisto(double valorPrevisto) {
        this.valorPrevisto = valorPrevisto;
    }

    public double getValorReal() {
        return valorReal;
    }

    public void setValorReal(double valorReal) {
        this.valorReal = valorReal;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }
}
