package com.ai.acompanha.acompanhaai.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ai.acompanha.acompanhaai.data.contract.RegistroContract;
import com.ai.acompanha.acompanhaai.data.dbhelper.RegistroDbHelper;
import com.ai.acompanha.acompanhaai.data.model.Registro;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class RegistroRepository {

    private Context context;
    RegistroDbHelper dbHelper;

    public RegistroRepository(Context ctx) {
        this.context = ctx;
        this.dbHelper = new RegistroDbHelper(this.context);
    }


    public long insert(Registro registro) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RegistroContract.RegistroEntry.COLUMN_CONSUMO, registro.getConsumo());
        values.put(RegistroContract.RegistroEntry.COLUMN_VALOR_ESTIMADO, registro.getValorPrevisto());
        values.put(RegistroContract.RegistroEntry.COLUMN_VALOR_REAL, registro.getValorReal());
        values.put(RegistroContract.RegistroEntry.COLUMN_PERIODO, registro.getPeriodo().toString());

        long newRowId = db.insert(RegistroContract.RegistroEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public ArrayList<Registro> buscarRegistros() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        return null;


    }
}
