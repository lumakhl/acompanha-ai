package com.ai.acompanha.acompanhaai.data.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtils {

    private static final String KEY = "key";
    private static final String CONSUMO_ANTERIOR = "consumo_anterior";
    private static final String CONSUMO = "consumo";
    private static final String VALOR = "valor";
    private static final String LOGADO = "logado";

    public static void setConsumoAnterior(Context context, int consumo) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(CONSUMO_ANTERIOR, consumo);
        editor.commit();
    }

    public static int getConsumoAnterior(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(CONSUMO_ANTERIOR, 0);
    }

    public static void setConsumo(Context context, int consumo) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(CONSUMO, consumo);
        editor.commit();
    }

    public static int getConsumo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getInt(CONSUMO, 0);
    }

    public static void setValor(Context context, float valor) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putFloat(VALOR, valor);
        editor.commit();
    }

    public static float getValor(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getFloat(VALOR, 0);
    }

    public static void setLogado(Context context, boolean logado){
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(LOGADO, logado);
        editor.commit();
    }

    public static boolean getLogado(Context context){
        SharedPreferences sp = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        return sp.getBoolean(LOGADO, false);
    }
}
