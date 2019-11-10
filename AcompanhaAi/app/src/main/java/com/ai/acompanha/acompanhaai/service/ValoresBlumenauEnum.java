package com.ai.acompanha.acompanhaai.service;

public enum ValoresBlumenauEnum {
    A(31.35),
    B(5.70),
    C(7.27);

    private final double valor;

    ValoresBlumenauEnum(double valor) {
        this.valor = valor;
    }

    public double getValor() {
        return this.valor;
    }

}
