package com.ai.acompanha.acompanhaai.service;

public class BlumenauCalculoService implements ICalculaService {

    private BlumenauCalculoService instance;

    private BlumenauCalculoService() {
    }

    private BlumenauCalculoService getInstance() {
        if (instance == null)
            instance = new BlumenauCalculoService();
        return instance;
    }

    @Override
    public double calcularValor(int anterior, int atual) {
        int consumo = calculaConsumo(anterior, atual);

        double valor = ValoresBlumenauEnum.A.getValor();

        if (consumo > 10) {
            consumo = consumo - 10;

            if (consumo > 19) {
                consumo = consumo - 19;
                valor += calculaPorFaixa(ValoresBlumenauEnum.B, 19);

                valor += calculaPorFaixa(ValoresBlumenauEnum.C, consumo);
            } else {
                valor += calculaPorFaixa(ValoresBlumenauEnum.B, consumo);
            }

        }
        return valor;

    }

    @Override
    public int calculaConsumo(int anterior, int atual) {
        return atual - anterior;
    }

    private double calculaPorFaixa(ValoresBlumenauEnum val, int consumo) {
        return val.getValor() * consumo;
    }
}
