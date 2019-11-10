package com.ai.acompanha.acompanhaai.data.contract;

import android.provider.BaseColumns;

public final class RegistroContract {

    private RegistroContract() {
    }

    public static class RegistroEntry implements BaseColumns {
        public static final String TABLE_NAME = "registro";
        public static final String COLUMN_CONSUMO = "consumo";
        public static final String COLUMN_VALOR_ESTIMADO = "valor_estimado";
        public static final String COLUMN_VALOR_REAL = "valor_real";
        public static final String COLUMN_PERIODO = "periodo";
    }


}
