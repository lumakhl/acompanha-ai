package com.ai.acompanha.acompanhaai.data.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    private static final String KEY = "key";
    private static final String CONSUMO_ANTERIOR = "consumo_anterior";
    private static final String CONSUMO = "consumo";
    private static final String VALOR = "valor";

    public void setConsumoAnterior(Context context, int consumo) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(CONSUMO_ANTERIOR, consumo);
        editor.commit();
    }

    public int getConsumoAnterior(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(CONSUMO_ANTERIOR, 0);
    }

    public void setConsumo(Context context, int consumo) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(CONSUMO, consumo);
        editor.commit();
    }

    public int getConsumo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(CONSUMO, 0);
    }

    public void setValor(Context context, float valor) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putFloat(VALOR, valor);
        editor.commit();
    }

    public float getValor(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getFloat(VALOR, 0);
    }
}
